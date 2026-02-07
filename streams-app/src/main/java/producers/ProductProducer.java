package producers;

import com.example.kafka.model.Order;
import com.example.kafka.model.OrderKey;
import com.example.kafka.model.Product;
import com.example.kafka.model.ProductKey;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.KeyValue;

import java.util.Properties;

import static topologies.OrderInfoTopology.ORDER_TOPIC;
import static topologies.ProductInfoTopology.PRODUCT_TOPIC;

public class ProductProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put("schema.registry.url", "http://127.0.0.1:8081");

        Producer<ProductKey, Product> producer = new KafkaProducer<>(props);
        KeyValue<ProductKey, Product> myProduct = new KeyValue<>(
                new ProductKey("15"),
                Product.newBuilder()
                        .setProductId("15")
                        .setName("Cool Product")
                        .setUnitPrice(9.99)
                        .build()
        );
        ProducerRecord<ProductKey, Product> record = new ProducerRecord<>(PRODUCT_TOPIC, myProduct.key, myProduct.value);

        producer.send(record);

        producer.close();
    }
}
