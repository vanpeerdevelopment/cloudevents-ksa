package be.vanpeer.cloudevents.ksa.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

import static java.nio.charset.StandardCharsets.UTF_8;

public record ProductUpdated(String isbn, String name, String description, Double price) {

    public byte[] toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this).getBytes(UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
