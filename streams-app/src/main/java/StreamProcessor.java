import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import topologies.StreamTopologyBuilder;

import java.util.Properties;

public class StreamProcessor {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "practice-streams-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        Topology topology = StreamTopologyBuilder.buildTopology();

        KafkaStreams streams = new KafkaStreams(topology, props);
        streams.start();

        // Close shutdown hook to maintain clean state
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}