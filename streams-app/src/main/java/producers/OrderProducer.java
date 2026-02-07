package producers;

import com.example.kafka.model.Order;
import com.example.kafka.model.OrderKey;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.KeyValue;

import java.util.Properties;

import static com.example.kafka.model.Order.newBuilder;
import static topologies.OrderInfoTopology.ORDER_TOPIC;

public class OrderProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put("schema.registry.url", "http://127.0.0.1:8081");

        Producer<OrderKey, Order> producer = new KafkaProducer<>(props);
        KeyValue<OrderKey, Order> myOrder = new KeyValue<>(
                new OrderKey("6", 10),
                Order.newBuilder()
                        .setOrderId("6")
                        .setProductId("15")
                        .setCustomerId("302")
                        .setQuantity(2)
                        .build()
        );
        ProducerRecord<OrderKey, Order> record = new ProducerRecord<>(ORDER_TOPIC, myOrder.key, myOrder.value);

        producer.send(record);

        producer.close();
    }
}
