package joiners;
import com.example.kafka.model.Order;
import com.example.kafka.model.Product;
import org.apache.kafka.streams.kstream.ValueJoiner;

public class ProductJoiner implements ValueJoiner<Order, Product, Order> {
    public Order apply(Order order, Product product) {
        return Order.newBuilder(order).build();
    }
}
