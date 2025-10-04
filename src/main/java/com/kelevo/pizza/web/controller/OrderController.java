package com.kelevo.pizza.web.controller;

import com.kelevo.pizza.persistence.entity.PizzaOrderEntity;
import com.kelevo.pizza.persistence.projection.OrderSumary;
import com.kelevo.pizza.services.OrderService;
import com.kelevo.pizza.services.dto.RandomOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<PizzaOrderEntity>> getAll() {
        return ResponseEntity.ok(this.orderService.getAll());
    }

    @GetMapping("/today")
    public ResponseEntity<List<PizzaOrderEntity>> getTodayOrders() {
        return ResponseEntity.ok(this.orderService.getTodayOrders());
    }

    @GetMapping("/outside")
    public ResponseEntity<List<PizzaOrderEntity>> getOutsideOrders() {
        return ResponseEntity.ok(this.orderService.getOutsideOrders());
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<PizzaOrderEntity>> getCustomerOrders(@PathVariable String id) {
        return ResponseEntity.ok(this.orderService.getCustomerOrders(id));
    }

    @GetMapping("/sumary/{id}")
    public ResponseEntity<OrderSumary> getSumary(@PathVariable int id) {
        return ResponseEntity.ok(this.orderService.getSumary(id));
    }

    @PostMapping("/random")
    public ResponseEntity<Boolean> randomOrden(@RequestBody RandomOrderDTO dto) {
        return ResponseEntity.ok(this.orderService.saveRandomOrder(dto));
    }

}
