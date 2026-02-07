package topologies;

import com.example.kafka.model.Product;
import com.example.kafka.model.ProductKey;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;

import static serdes.AppSerdes.productKeySerde;
import static serdes.AppSerdes.productValueSerde;

public final class ProductInfoTopology {
    public static final String PRODUCT_TOPIC = "product";
    private ProductInfoTopology() {}

    public static final KTable<ProductKey, Product> build(StreamsBuilder builder) {
        return builder.table(
                PRODUCT_TOPIC,
                Consumed.with(productKeySerde, productValueSerde),
                Materialized.as("ktable-product")
        );
    }
}
