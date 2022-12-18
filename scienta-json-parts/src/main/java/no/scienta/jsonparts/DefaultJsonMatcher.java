package no.scienta.jsonparts;

import java.util.Collections;

import com.fasterxml.jackson.databind.JsonNode;

public record DefaultJsonMatcher(JsonNode node) implements JsonMatcher {

    @Override
    public boolean contains(JsonNode part) {
        return match(part).matches();
    }

    @Override
    public StructuralMatch match(JsonNode part) {
        return Collections::emptyMap;
    }
}
