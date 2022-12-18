package no.scienta.jsonparts;

import com.fasterxml.jackson.databind.JsonNode;

@FunctionalInterface
public interface JsonMatcher {

    boolean contains(JsonNode part);
}
