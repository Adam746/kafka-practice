import joiners.ProductJoiner;
import mappers.MapperTemplate;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KTable;
import topologies.OrderInfoTopology;
import topologies.ProductInfoTopology;

class StreamProcessor {
    public static final String PRODUCT_TOPIC = "product";
    public static final String ORDER_TOPIC = "order";
    public void createTopology(StreamsBuilder builder) {
        KTable<String, String> stagingProduct = ProductInfoTopology.build(builder);
        KTable<String, String> stagingOrder = OrderInfoTopology.build(builder);

        stagingOrder
                .join(
                        stagingProduct,
                        new ProductJoiner()
                )
                .toStream()
                .mapValues(new MapperTemplate())
                .to(
                        "practice-output"
                );
    }
}