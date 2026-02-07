package joiners;
import org.apache.kafka.streams.kstream.ValueJoiner;

public class ProductJoiner implements ValueJoiner<String, String, String> {
    public String apply(String order, String product) {
        return "Order: " + order + " | Product: " + product;
    }
}
