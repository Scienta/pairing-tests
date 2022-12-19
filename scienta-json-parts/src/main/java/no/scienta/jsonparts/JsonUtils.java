package no.scienta.jsonparts;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.databind.JsonNode;

@SuppressWarnings("unused")
final class JsonUtils {

    /**
     * @param json Node that {@link JsonNode#isObject() is an object}
     * @param map  Map function that maps an object field
     * @param <T>  Mapped type
     *
     * @return Stream of mapped object node fields
     */
    static <T> Stream<T> mapNamedFields(JsonNode json, BiFunction<String, JsonNode, Stream<T>> map) {
        return namedFields(json).flatMap(e ->
            map.apply(e.getKey(), e.getValue()));
    }

    /**
     * @param json Node that {@link JsonNode#isArray() is an array}
     * @param <T>  Mapped type
     *
     * @return Stream of mapped array elements
     */
    static <T> Stream<T> mapArrayElements(JsonNode json, Function<JsonNode, Stream<T>> map) {
        return arrayElements(json).flatMap(map);
    }

    /**
     * @param json Node that {@link JsonNode#isObject() is an object}
     *
     * @return Stream of object node's fields as map entries
     */
    static Stream<Map.Entry<String, JsonNode>> namedFields(JsonNode json) {
        return stream(json::fields);
    }

    /**
     * @param json Node that {@link JsonNode#isArray() is an array}
     *
     * @return Stream of the array's elements
     */
    static Stream<JsonNode> arrayElements(JsonNode json) {
        return stream(json::elements);
    }

    static List<String> addTo(List<String> list, String added) {
        return list == null
            ? Collections.singletonList(added)
            : Stream.concat(list.stream(), Stream.of(added)).toList();
    }

    private JsonUtils() {
    }

    private static <T> Stream<T> stream(Iterable<T> elements) {
        return StreamSupport.stream(elements.spliterator(), false);
    }
}
