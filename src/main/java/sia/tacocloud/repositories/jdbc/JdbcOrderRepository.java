package sia.tacocloud.repositories.jdbc;

import org.springframework.stereotype.Repository;
import sia.tacocloud.concepts.Order;
import sia.tacocloud.repositories.OrderRepository;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    @Override
    public Order save(Order order) {
        return null;
    }
}
