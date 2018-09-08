package org.sandbox.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.sandbox.utils.DateFormatter;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeSqlDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return DateFormatter.parseLocalDateTime(parser.getValueAsString(), DateFormatter.PATTERN_SQL_DATE_TIME);
    }
}
