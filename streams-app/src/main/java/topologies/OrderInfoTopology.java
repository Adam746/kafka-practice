package topologies;

import com.example.kafka.model.Order;
import com.example.kafka.model.OrderKey;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;

import static serdes.AppSerdes.orderKeySerde;
import static serdes.AppSerdes.orderValueSerde;

public final class OrderInfoTopology {
    public static final String ORDER_TOPIC = "order";
    private OrderInfoTopology() {}

    public static final KTable<OrderKey, Order> build(StreamsBuilder builder) {
        return builder.table(
                ORDER_TOPIC,
                Consumed.with(orderKeySerde, orderValueSerde),
                Materialized.as("ktable-order")
        );
    }
}
