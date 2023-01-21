package no.scienta.jsonparts;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JsonNode;

import static no.scienta.jsonparts.JsonUtils.addTo;
import static no.scienta.jsonparts.JsonUtils.arrayElements;
import static no.scienta.jsonparts.JsonUtils.mapArrayElements;
import static no.scienta.jsonparts.JsonUtils.mapNamedFields;

public record DefaultJsonMatcher(JsonNode node) implements JsonMatcher {

    @Override
    public boolean contains(JsonNode part) {
        return pathsIn(part)
            .map(path ->
                path.through(node))
            .allMatch(Pathway::found);
    }

    private Stream<Path> pathsIn(JsonNode part) {
        if (part.isObject()) {
            return mapNamedFields(part, (name, subNode) ->
                pathsIn(subNode).map(path ->
                    new Step(name, path)));
        }
        if (part.isArray()) {
            return mapArrayElements(part, this::pathsIn)
                .map(Fork::new);
        }
        return Stream.of(new Destination(part));
    }

    private sealed interface Path {

        default Pathway through(JsonNode json) {
            return through(json, null);
        }

        Pathway through(JsonNode json, List<String> trace);
    }

    private record Step(String name, Path next) implements Path {

        @Override
        public Pathway through(JsonNode json, List<String> trace) {
            return json.hasNonNull(name)
                ? next.through(json.get(name), addTo(trace, name))
                : new Pathway(null, json, trace);
        }
    }

    private record Fork(Path path) implements Path {

        @Override
        public Pathway through(JsonNode json, List<String> trace) {
            return arrayElements(json).map(node ->
                    path.through(node, trace))
                .filter(Pathway::found)
                .findFirst()
                .orElseGet(() ->
                    new Pathway(null, json, trace));
        }
    }

    private record Destination(JsonNode expected) implements Path {

        @Override
        public Pathway through(JsonNode json, List<String> trace) {
            return new Pathway(json, expected, trace);
        }
    }

    private record Pathway(JsonNode endNode, JsonNode expected, List<String> trace) {

        public boolean found() {
            return Objects.equals(endNode, expected);
        }
    }
}
