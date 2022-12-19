package no.scienta.jsonparts;

import com.fasterxml.jackson.databind.JsonNode;

public record DefaultJsonMatcher(JsonNode node) implements JsonMatcher {

    @Override
    public boolean contains(JsonNode part) {
        return false;
    }
}
