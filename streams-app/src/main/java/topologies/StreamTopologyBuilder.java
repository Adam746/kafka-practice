package topologies;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;

public class StreamTopologyBuilder {

    // Move the logic here!
    public static Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();

        builder.stream("practice-input")
                .mapValues(value -> value.toString().toUpperCase())
                .to("practice-output");

        return builder.build();
    }
}
