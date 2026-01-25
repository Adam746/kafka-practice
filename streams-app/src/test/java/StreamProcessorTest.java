import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import topologies.StreamTopologyBuilder;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamProcessorTest {
    private TopologyTestDriver testDriver;
    private TestInputTopic<String, String> inputTopic;
    private TestOutputTopic<String, String> outputTopic;

    @BeforeEach
    void setup() {
        Topology topology = StreamTopologyBuilder.buildTopology();

        // 2. Setup Dummy Config
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // 3. Create the Driver
        testDriver = new TopologyTestDriver(topology, props);

        // 4. Create handles for topics
        inputTopic = testDriver.createInputTopic("practice-input", Serdes.String().serializer(), Serdes.String().serializer());
        outputTopic = testDriver.createOutputTopic("practice-output", Serdes.String().deserializer(), Serdes.String().deserializer());
    }

    @AfterEach
    void tearDown() {
        testDriver.close();
    }

    @Test
    void shouldUppercaseInput() {
        inputTopic.pipeInput("hello");
        assertEquals("HELLO", outputTopic.readValue());
    }
}