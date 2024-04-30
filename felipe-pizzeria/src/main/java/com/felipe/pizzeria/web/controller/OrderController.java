package com.felipe.pizzeria.web.controller;

import com.felipe.pizzeria.persistence.entity.OrderEntity;
import com.felipe.pizzeria.persistence.projection.OrderSummary;
import com.felipe.pizzeria.service.OrderService;
import com.felipe.pizzeria.service.dto.RandomOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity <List<OrderEntity>> getAll(){
        return ResponseEntity.ok(this.orderService.getAll());
    }

    @GetMapping("/today")
    public ResponseEntity<List<OrderEntity>> getTodayOrders(){
        return ResponseEntity.ok(this.orderService.getTodayOrders());
    }

    @GetMapping("/past-orders")
    public ResponseEntity<List<OrderEntity>> getPastOrders(){
        return ResponseEntity.ok(this.orderService.getPastOrders());
    }

    @GetMapping("/outside")
    public ResponseEntity<List<OrderEntity>> getOutsideOrders(){
        return ResponseEntity.ok(this.orderService.getOutsideOrders());
    }

    @GetMapping("/inside")
    public ResponseEntity<List<OrderEntity>> getInsideOrders(){
        return ResponseEntity.ok(this.orderService.getInsideOrders());
    }
    @GetMapping("/customers/{id}")
    public ResponseEntity<List<OrderEntity>> getCustomersOrders(@PathVariable String id){
        return ResponseEntity.ok(this.orderService.getCustomerOrders(id));
    }

    @GetMapping("/summary/{id}")
    public ResponseEntity<OrderSummary> getSummary(@PathVariable int id) {
        return ResponseEntity.ok(this.orderService.getSummary(id));
    }

    @PostMapping("/random")
    public ResponseEntity<Boolean> randomOrder(@RequestBody RandomOrderDto dto){
        return ResponseEntity.ok(this.orderService.saveRandomOrder(dto));
    }

}
