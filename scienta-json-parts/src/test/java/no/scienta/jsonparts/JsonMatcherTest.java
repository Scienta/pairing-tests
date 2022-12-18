package no.scienta.jsonparts;

import java.util.List;
import java.util.Map;

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
        matcher = new DefaultJsonMatcher(json(
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
              "arr2": [ "dip", 5, true ]
            }
            """));
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
    void deviatingPathsAreRetruned() {
        assertThat(
            match("""
                  {
                    "foo": {
                      "bar": 4,
                      "zot": {
                      }
                    },
                    "arr2": [ true, "ouch" ]
                  }
                  """).unmatched()).containsExactly(
            Map.entry(
                List.of("arr2"),
                json(
                    """
                    [ "dip", 5, true ]
                    """))
        );
    }

    private JsonMatcher.StructuralMatch match(String json) {
        return matcher.match(json(json));
    }

    private void assertNotPart(String content) {
        assertThat(matcher.contains(json(content))).isFalse();
    }

    private void assertPart(String content) {
        assertThat(matcher.contains(json(content))).isTrue();
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static JsonNode json(String content) {
        try {
            return OBJECT_MAPPER.readTree(content);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse: " + content, e);
        }
    }
}
