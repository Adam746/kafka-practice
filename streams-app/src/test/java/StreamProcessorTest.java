import com.example.kafka.model.Order;
import com.example.kafka.model.OrderKey;
import com.example.kafka.model.Product;
import com.example.kafka.model.ProductKey;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serdes.AppSerdes;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import static com.example.kafka.model.Product.newBuilder;
import static org.junit.jupiter.api.Assertions.*;
import static serdes.AppSerdes.*;

public class StreamProcessorTest {
    private TopologyTestDriver driver;
    private TestInputTopic<OrderKey, Order> orderTopic;
    private TestInputTopic<ProductKey, Product> productTopic;
    private TestOutputTopic<OrderKey, Order> outputTopic;
    KeyValue<OrderKey, Order> myOrder;
    KeyValue<ProductKey, Product> myProduct;

    @BeforeEach
    void setup() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        Map<String, String> config = Collections.singletonMap("schema.registry.url", "mock://test-schema-reg");
        AppSerdes.init("mock://test-schema-reg");
        StreamsBuilder builder = new StreamsBuilder();
        StreamProcessor streamProcessor = new StreamProcessor();
        streamProcessor.createTopology(builder);
        driver = new TopologyTestDriver(builder.build(props), props);

        orderTopic = driver.createInputTopic("order", orderKeySerde.serializer(), orderValueSerde.serializer());
        productTopic = driver.createInputTopic("product", productKeySerde.serializer(), productValueSerde.serializer());
        outputTopic = driver.createOutputTopic("practice-output", orderKeySerde.deserializer(), orderValueSerde.deserializer());

        myProduct = new KeyValue<>(
                new ProductKey("20"),
                Product.newBuilder()
                        .setProductId("20")
                        .setName("My Best Product")
                        .setUnitPrice(15.99)
                        .build()
        );
        myOrder = new KeyValue<>(
                new OrderKey("6", 10),
                Order.newBuilder()
                        .setOrderId("6")
                        .setProductId("20")
                        .setCustomerId("302")
                        .setQuantity(2)
                        .build()
        );
    }

    @AfterEach
    void tearDown() {
        driver.close();
    }

    @Test
    void shouldProduceOutput() {
        orderTopic.pipeInput(myOrder.key, myOrder.value);
        productTopic.pipeInput(myProduct.key, myProduct.value);
        assertFalse(outputTopic.isEmpty());
    }

    @Test
    void shouldProduceExpectedOutput() {
        orderTopic.pipeInput(myOrder.key, myOrder.value);
        productTopic.pipeInput(myProduct.key, myProduct.value);
        assertFalse(outputTopic.isEmpty());
        assertEquals(myOrder.value, outputTopic.readValue());
    }

    @Test
    void shouldNotProduceOutput() {
        orderTopic.pipeInput(myOrder.key, myOrder.value);
        assertTrue(outputTopic.isEmpty());
    }
}