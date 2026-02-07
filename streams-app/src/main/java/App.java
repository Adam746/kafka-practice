import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import serdes.AppSerdes;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

public class App {
    public static final String PRODUCT_TOPIC = "product";
    public static final String ORDER_TOPIC = "order";
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.TOPOLOGY_OPTIMIZATION_CONFIG, StreamsConfig.NO_OPTIMIZATION);
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "practice-streams-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(StreamsConfig.STATE_DIR_CONFIG, "C:/kafka-work/state");
        props.put(ConsumerConfig.DEFAULT_API_TIMEOUT_MS_CONFIG, 10000);
        props.put(StreamsConfig.REQUEST_TIMEOUT_MS_CONFIG, 10000);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        //props.put(SCHEMA_REGISTRY_URL_CONFIG, "http://127.0.0.1:8081");
        props.put(StreamsConfig.mainConsumerPrefix(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG), 10000);
        props.put(StreamsConfig.mainConsumerPrefix(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG), 500);
        props.put(StreamsConfig.mainConsumerPrefix(ConsumerConfig.RECEIVE_BUFFER_CONFIG), 65536);
        props.put(StreamsConfig.consumerPrefix(ConsumerConfig.DEFAULT_API_TIMEOUT_MS_CONFIG), 10000);

        AppSerdes.init("http://127.0.0.1:8081");
        CountDownLatch latch = new CountDownLatch(1);
        StreamsBuilder builder = new StreamsBuilder();
        StreamProcessor streamProcessor = new StreamProcessor();
        streamProcessor.createTopology(builder);

        KafkaStreams streams = new KafkaStreams(builder.build(props), props);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            streams.close();
            latch.countDown();
        }));

        try {
            streams.cleanUp();
            System.out.println("DEBUG: Starting Streams...");
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
