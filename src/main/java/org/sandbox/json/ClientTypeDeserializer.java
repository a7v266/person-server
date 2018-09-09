package org.sandbox.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.sandbox.domain.ClientType;
import org.sandbox.domain.Reference;

import java.io.IOException;

public class ClientTypeDeserializer extends JsonDeserializer<ClientType> {

    @Override
    public ClientType deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return Reference.getReference(ClientType.class, parser.getValueAsInt());
    }
}
