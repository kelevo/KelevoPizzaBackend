package com.kelevo.pizza.services;

import com.kelevo.pizza.persistence.entity.PizzaOrderEntity;
import com.kelevo.pizza.persistence.projection.OrderSumary;
import com.kelevo.pizza.persistence.repository.OrderRepository;
import com.kelevo.pizza.services.dto.RandomOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    private static final String DELIVERY = "D";
    private static final String CARRYOUT = "C";
    private static final String ON_SITE = "S";


    public List<PizzaOrderEntity> getAll() {
        return this.orderRepository.findAll();
    }

    public List<PizzaOrderEntity> getTodayOrders() {
        LocalDateTime today = LocalDate.now().atTime(0, 0);
        return this.orderRepository.findAllByDateAfter(today);
    }

    public List<PizzaOrderEntity> getOutsideOrders() {
        List<String> methods = Arrays.asList(DELIVERY, CARRYOUT);
        return this.orderRepository.findAllByMethodIn(methods);
    }

    @Secured("ROLE_ADMIN")
    public List<PizzaOrderEntity> getCustomerOrders(String idCustomer) {
        return this.orderRepository.findCustomerOrders(idCustomer);
    }

    public OrderSumary getSumary(int orderId) {
        return this.orderRepository.findSumary(orderId);
    }

    @Transactional
    public boolean saveRandomOrder(RandomOrderDTO randomOrderDto) {
        return this.orderRepository.saveRandomOrder(randomOrderDto.getIdCustomer(), randomOrderDto.getMethod());
    }

}
