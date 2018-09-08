package org.sandbox.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.sandbox.utils.StringUtils;

import java.io.IOException;

public class StringWebDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return StringUtils.clean(parser.getValueAsString());
    }
}
