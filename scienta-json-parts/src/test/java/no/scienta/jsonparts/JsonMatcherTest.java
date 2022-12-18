package no.scienta.jsonparts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SameParameterValue")
class JsonMatcherTest {

    private JsonMatcher matcher;

    @BeforeEach
    void setUp() {
        matcher = jsonMatcher(
            """
            {
              "foo": {
                "bar": 4,
                "zot": {
                  "zip": 45.4,
                  "zips": [
                   { "argh": [ 4, 5, 6 ]},
                   { "rarg": [ 3, 2, 1 ]}
                  ]
                }
              },
              "arr2": [ "dip", 5, true ],
              "departments": [
                {
                    "tech": {
                      "employees": [
                         { "name": "Harry", "salary": 4.5 },
                         { "name": "Sally", "salary": 5.5 }
                      ]
                  }
                },
                {
                    "sales": {
                      "employees": [
                        { "name": "Dumb", "salary": 10.5 },
                        { "name": "Dumber", "salary": 11.5 }
                      ]
                    }
                }
              ]
            }
            """);
    }

    @Test
    void simpleSubsetIsPart() {
        assertPart(
            """
            {
              "foo":
              {
                "bar": 4
              }
            }
            """
        );
    }

    @Test
    void simpleDeviatingSubsetIsNotPart() {
        assertNotPart(
            """
            {
              "foo": {
                "bar": 5
              }
            }
            """
        );
    }

    @Test
    void explicitNullIsNotPart() {
        assertNotPart(
            """
            {
              "foo": {
                "bar": null
              }
            }
            """
        );
    }

    @Test
    void notPartIfArrayIsDifferent() {
        assertNotPart(
            """
            {
              "foo": {
                "bar": 4,
                "zot": {
                  "zips": [
                    { "argh": [7, 8] }
                  ]
                }
              }
            }
            """);
        assertNotPart(
            """
            {
              "foo": {
                "bar": 4,
                "zot": {
                }
              },
              "arr2": [ true, "ouch" ]
            }
            """);
    }

    @Test
    void isPartIfArrayIsSubset() {
        assertPart(
            """
            {
              "foo": {
                "bar": 4,
                "zot": {
                  "zips": [
                    { "rarg": [3]}
                  ]
                }
              }
            }
            """);
    }

    @Test
    void isPartIfArrayIsSubsetRegardlessOfOrder() {
        assertPart(
            """
            {
              "foo": {
                "bar": 4,
                "zot": {
                  "zips": [
                    { "argh": [6, 5]}
                  ]
                }
              }
            }
            """);
    }

    @Test
    void isPartIfArrayIsSubsetRegardlessOfArity() {
        assertPart(
            """
            {
              "foo": {
                "bar": 4,
                "zot": {
                  "zips": [
                    { "argh": [6, 5, 5, 5, 5]}
                  ]
                }
              }
            }
            """);
    }

    @Test
    void notPartIfArrayHasAdditionalElements() {
        assertNotPart(
            """
            {
              "foo": {
                "bar": 4,
                "zot": {
                  "zips": [
                    { "argh": [3, 4, 5, 6]}
                  ]
                }
              }
            }
            """);
    }

    @Test
    void notPartIfArrayHasExplicityNulls() {
        assertPart(
            """
            {
              "foo": {
                "bar": 4,
                "zot": {
                  "zips": [
                    { "argh": [4]}
                  ]
                }
              }
            }
            """);
        assertNotPart(
            """
            {
              "foo": {
                "bar": 4,
                "zot": {
                  "zips": [
                    { "argh": [null]}
                  ]
                }
              }
            }
            """);
    }

    @Test
    void deviatingPathsThroughListsAreReturned() {
        assertPart(
            """
            {
              "departments": [
                {
                  "tech": {
                    "employees": [
                       { "name": "Harry" }
                    ]
                  }
                }
              ]
            }
            """);
        assertNotPart(
            """
            {
              "departments": [
                {
                  "sales": {
                    "employees": [
                       { "name": "Harry" }
                    ]
                  }
                }
              ]
            }
            """);
    }

    @Test
    void arrayTest() {
        JsonMatcher jsonMatcher = jsonMatcher(
            """
            { "foo": "bar" }
            """);
        assertNotPart("[3]");
    }

    private void assertNotPart(String content) {
        assertNotPart(matcher, content);
    }

    private void assertPart(String content) {
        assertThat(matcher.contains(json(content))).isTrue();
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static void assertNotPart(JsonMatcher matcher, String content) {
        assertThat(matcher.contains(json(content))).isFalse();
    }

    private static JsonMatcher jsonMatcher(String content) {
        return new DefaultJsonMatcher(json(content));
    }

    private static JsonNode json(String content) {
        try {
            return OBJECT_MAPPER.readTree(content);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse: " + content, e);
        }
    }
}
