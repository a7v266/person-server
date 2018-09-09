package org.sandbox.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.sandbox.domain.Reference;

import java.io.IOException;

public class ReferenceSerializer extends JsonSerializer<Reference> {

    @Override
    public void serialize(Reference value, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        if (value != null) {
            generator.writeNumber(value.getId());
        }
    }
}