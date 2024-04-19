/**
 * Autor: ChatGPT
 * Link: https://chat.openai.com/share/0b0bb792-e8f8-42b1-a813-5776ac7cde55
 * Version: 1.0.0
 */
package ch.hslu.informatik.swde.rws.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * This class is a JAX-RS ContextResolver that provides a customized ObjectMapper instance for JSON serialization and deserialization.
 * It is used to configure and provide an ObjectMapper with specific settings for handling JSON data in JAX-RS applications.
 */
@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {
    private final ObjectMapper MAPPER;

    /**
     * Constructs an ObjectMapperContextResolver and configures the ObjectMapper instance.
     */
    public ObjectMapperContextResolver() {
        MAPPER = new ObjectMapper();
        // Register the JSR310Module to handle Java 8 Date and Time API types.
        MAPPER.registerModule(new JSR310Module());
        // Configure to write dates as ISO 8601 timestamps instead of numeric timestamps.
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    /**
     * Gets the ObjectMapper instance to be used for JSON serialization and deserialization.
     *
     * @param type The class type for which the ObjectMapper is requested.
     * @return The configured ObjectMapper instance.
     */
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return MAPPER;
    }
}
