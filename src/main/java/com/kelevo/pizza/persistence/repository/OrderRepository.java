package com.kelevo.pizza.persistence.repository;

import com.kelevo.pizza.persistence.entity.PizzaOrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends ListCrudRepository<PizzaOrderEntity, Integer> {

    List<PizzaOrderEntity> findAllByDateAfter(LocalDateTime date);

    List<PizzaOrderEntity> findAllByMethodIn(List<String> methods);

    @Query(value = "SELECT * FROM pizza_order WHERE id_customer = :id", nativeQuery = true)
    List<PizzaOrderEntity> findCustomerOrders(@Param("id") String idCustomer);

}
