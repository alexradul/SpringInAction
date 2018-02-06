package sia.tacocloud.repositories;

import sia.tacocloud.concepts.Order;

public interface OrderRepository {
    Order save(Order order);
}
