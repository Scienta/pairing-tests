package no.scienta.jsonparts;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public interface JsonMatcher {

    @FunctionalInterface
    interface StructuralMatch {

        default boolean matches() {
            return unmatched().isEmpty();
        }

        Map<List<String>, JsonNode> unmatched();
    }

    default boolean contains(JsonNode part) {
        return match(part).unmatched().isEmpty();
    }

    StructuralMatch match(JsonNode part);
}
