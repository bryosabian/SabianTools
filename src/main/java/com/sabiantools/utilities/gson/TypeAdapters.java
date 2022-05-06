package com.sabiantools.utilities.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class TypeAdapters {

    /**
     * Gets rid of the NumberFormatException when handling empty strings on Long tokens
     */
    public static class LongTypeAdapter extends TypeAdapter<Long> {

        private Long defaultValue = null;

        public LongTypeAdapter() {
        }

        public LongTypeAdapter(long defaultValue) {
            this.defaultValue = defaultValue;
        }

        @Override
        public Long read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return defaultValue;
            }
            String stringValue = reader.nextString();
            try {
                return Long.valueOf(stringValue);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }

        @Override
        public void write(JsonWriter writer, Long value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }
}
