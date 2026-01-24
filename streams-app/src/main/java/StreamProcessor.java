import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class StreamProcessor {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "practice-streams-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        
        // 1. Source
        KStream<String, String> inputLines = builder.stream("practice-input");

        // 2. Process (Uppercase)
        KStream<String, String> uppercasedLines = inputLines.mapValues(value -> value.toUpperCase());

        // 3. Sink
        uppercasedLines.to("practice-output");

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        // Close shutdown hook to maintain clean state
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}