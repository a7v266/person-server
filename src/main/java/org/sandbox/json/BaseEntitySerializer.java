package org.sandbox.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.sandbox.domain.BaseEntity;

import java.io.IOException;

public class BaseEntitySerializer extends JsonSerializer<BaseEntity> {

    @Override
    public void serialize(BaseEntity entity, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeNumber(entity.getId());
    }
}
