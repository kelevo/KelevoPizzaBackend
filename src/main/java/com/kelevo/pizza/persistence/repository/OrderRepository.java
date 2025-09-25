package com.kelevo.pizza.persistence.repository;

import com.kelevo.pizza.persistence.entity.PizzaOrderEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface OrderRepository extends ListCrudRepository<PizzaOrderEntity, Integer> {
}
