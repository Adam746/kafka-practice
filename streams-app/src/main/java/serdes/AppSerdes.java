package serdes;
import com.example.kafka.model.Order;
import com.example.kafka.model.OrderKey;
import com.example.kafka.model.Product;
import com.example.kafka.model.ProductKey;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;

import java.util.Collections;
import java.util.Map;

public class AppSerdes {
    public static SpecificAvroSerde<ProductKey> productKeySerde;
    public static SpecificAvroSerde<Product> productValueSerde;
    public static SpecificAvroSerde<OrderKey> orderKeySerde;
    public static SpecificAvroSerde<Order> orderValueSerde;

    public static void init(String url) {
        Map<String, String> serdeConfig = Collections.singletonMap(
                "schema.registry.url", url
        );
        productKeySerde = new SpecificAvroSerde<>();
        productKeySerde.configure(serdeConfig, true);

        productValueSerde = new SpecificAvroSerde<>();
        productValueSerde.configure(serdeConfig, false);

        orderKeySerde = new SpecificAvroSerde<>();
        orderKeySerde.configure(serdeConfig, true);

        orderValueSerde = new SpecificAvroSerde<>();
        orderValueSerde.configure(serdeConfig, false);
    }
}
