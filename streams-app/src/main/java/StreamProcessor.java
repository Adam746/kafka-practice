import com.example.kafka.model.Order;
import com.example.kafka.model.OrderKey;
import com.example.kafka.model.Product;
import com.example.kafka.model.ProductKey;
import joiners.ProductJoiner;
import mappers.MapperTemplate;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import topologies.OrderInfoTopology;
import topologies.ProductInfoTopology;

import static serdes.AppSerdes.orderKeySerde;
import static serdes.AppSerdes.orderValueSerde;

class StreamProcessor {
    public void createTopology(StreamsBuilder builder) {
        KTable<ProductKey, Product> stagingProduct = ProductInfoTopology.build(builder);
        KTable<OrderKey, Order> stagingOrder = OrderInfoTopology.build(builder);

        stagingOrder
                .join(
                        stagingProduct,
                        v -> new ProductKey(v.getProductId()),
                        new ProductJoiner()
                )
                .toStream()
                .mapValues(new MapperTemplate())
                .to(
                        "practice-output",
                        Produced.with(orderKeySerde,orderValueSerde)
                );
    }
}