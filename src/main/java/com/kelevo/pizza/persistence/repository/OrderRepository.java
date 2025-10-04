package com.kelevo.pizza.persistence.repository;

import com.kelevo.pizza.persistence.entity.PizzaOrderEntity;
import com.kelevo.pizza.persistence.projection.OrderSumary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends ListCrudRepository<PizzaOrderEntity, Integer> {

    List<PizzaOrderEntity> findAllByDateAfter(LocalDateTime date);

    List<PizzaOrderEntity> findAllByMethodIn(List<String> methods);

    @Query(value = "SELECT * FROM pizza_order WHERE id_customer = :id", nativeQuery = true)
    List<PizzaOrderEntity> findCustomerOrders(@Param("id") String idCustomer);

    @Query(value = "SELECT po.id_order AS idOrder, c.name AS customerName, po.date AS orderDate, po.total AS orderTotal, GROUP_CONCAT(p.name) AS pizzaNames " +
            "FROM pizza_order po " +
            "JOIN customer    c  ON po.id_customer = c.id_customer " +
            "JOIN order_item  oi ON po.id_order = oi.id_order " +
            "JOIN pizza       p  ON oi.id_pizza = p.id_pizza " +
            "WHERE po.id_order = :orderId " +
            "GROUP BY po.id_order, c.name, po.date, po.total",
            nativeQuery = true
    )
    OrderSumary findSumary(@Param("orderId") int orderId);

    @Procedure(value = "take_random_pizza_order", outputParameterName = "order_taken")
    boolean saveRandomOrder(@Param("id_customer") String idCustomer, @Param("method") String method);

}
