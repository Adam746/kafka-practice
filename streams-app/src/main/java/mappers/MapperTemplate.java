package mappers;

import com.example.kafka.model.Order;
import com.example.kafka.model.OrderKey;
import org.apache.kafka.streams.kstream.ValueMapperWithKey;

public class MapperTemplate implements ValueMapperWithKey<OrderKey, Order, Order> {
    @Override
    public Order apply(OrderKey key, Order value) {
        return value;
    }
}
