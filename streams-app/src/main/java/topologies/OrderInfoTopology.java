package topologies;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KTable;

public final class OrderInfoTopology {
    public static final String ORDER_TOPIC = "order";
    private OrderInfoTopology() {}

    public static final KTable<String, String> build(StreamsBuilder builder) {
        return builder.table(
                ORDER_TOPIC
        );
    }
}
