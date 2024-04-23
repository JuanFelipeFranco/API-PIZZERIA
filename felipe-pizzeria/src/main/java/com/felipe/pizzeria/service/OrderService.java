package com.felipe.pizzeria.service;

import com.felipe.pizzeria.persistence.entity.OrderEntity;
import com.felipe.pizzeria.persistence.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private  static final String DELIVERY = "D";
    private  static final String CARRYOUT = "C";
    private  static final String ON_SITE = "S";

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    //recuperamos todas las ordenes de la pizzeria
    public List<OrderEntity> getAll(){
        List<OrderEntity> orders = this.orderRepository.findAll();
        orders.forEach(o-> System.out.println(o.getCustomer().getName()));
        return orders;
    }

    public List<OrderEntity> getTodayOrders(){
        //nos carga una variable con la fecha actual a la hora 0:00
        LocalDateTime today = LocalDate.now().atTime(0,0);
        return this.orderRepository.findAllByDateAfter(today);
    }

    public List<OrderEntity> getPastOrders(){
        //nos carga una variable con la fecha actual a la hora 0:00
        LocalDateTime today = LocalDate.now().atTime(0,0);
        return this.orderRepository.findAllByDateBefore(today);
    }

    public List<OrderEntity> getOutsideOrders(){
        List<String> methods = Arrays.asList(DELIVERY, CARRYOUT);
        return this.orderRepository.findAllByMethodIn(methods);
    }

    public List<OrderEntity> getInsideOrders(){
        List<String> methods = Arrays.asList(ON_SITE);
        return this.orderRepository.findAllByMethodIn(methods);
    }
}