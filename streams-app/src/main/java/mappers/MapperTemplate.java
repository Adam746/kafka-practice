package mappers;

import org.apache.kafka.streams.kstream.ValueMapperWithKey;

public class MapperTemplate implements ValueMapperWithKey<String, String, String> {
    @Override
    public String apply(String key, String value) {
        return value;
    }
}
