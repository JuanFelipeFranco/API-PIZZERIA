package com.felipe.pizzeria.persistence.repository;

import com.felipe.pizzeria.persistence.entity.OrderEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends ListCrudRepository<OrderEntity, Integer> {
    List<OrderEntity> findAllByDateAfter(LocalDateTime date);
    List<OrderEntity> findAllByDateBefore(LocalDateTime date);

    //ordenes de domicilio o de recogifa;
    List<OrderEntity> findAllByMethodIn(List<String> methods);




}
