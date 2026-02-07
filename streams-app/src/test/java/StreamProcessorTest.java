import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class StreamProcessorTest {
    private TopologyTestDriver driver;
    private TestInputTopic<String, String> orderTopic;
    private TestInputTopic<String, String> productTopic;
    private TestOutputTopic<String, String> outputTopic;

    @BeforeEach
    void setup() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        StreamProcessor streamProcessor = new StreamProcessor();
        streamProcessor.createTopology(builder);
        driver = new TopologyTestDriver(builder.build(props), props);

        orderTopic = driver.createInputTopic("order", Serdes.String().serializer(), Serdes.String().serializer());
        productTopic = driver.createInputTopic("product", Serdes.String().serializer(), Serdes.String().serializer());
        outputTopic = driver.createOutputTopic("practice-output", Serdes.String().deserializer(), Serdes.String().deserializer());
    }

    @AfterEach
    void tearDown() {
        driver.close();
    }

    @Test
    void shouldProduceOutput() {
        orderTopic.pipeInput("1","Test Order");
        productTopic.pipeInput("1","Test Product");
        assertFalse(outputTopic.isEmpty());
        assertEquals("Order: Test Order | Product: Test Product", outputTopic.readValue());
    }

    @Test
    void shouldNotProduceOutput() {
        orderTopic.pipeInput("1","Test Order");
        productTopic.pipeInput("2","Test Product");
        assertTrue(outputTopic.isEmpty());
    }
}