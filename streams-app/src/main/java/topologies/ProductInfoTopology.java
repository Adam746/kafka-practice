package topologies;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KTable;

public final class ProductInfoTopology {
    public static final String PRODUCT_TOPIC = "product";
    private ProductInfoTopology() {}

    public static final KTable<String, String> build(StreamsBuilder builder) {
        return builder.table(
                PRODUCT_TOPIC
        );
    }
}
