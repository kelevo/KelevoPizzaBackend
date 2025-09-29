package com.kelevo.pizza.persistence.repository;

import com.kelevo.pizza.persistence.entity.PizzaOrderEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends ListCrudRepository<PizzaOrderEntity, Integer> {

    List<PizzaOrderEntity> findAllByDateAfter(LocalDateTime date);

    List<PizzaOrderEntity> findAllByMethodIn(List<String> methods);

}
