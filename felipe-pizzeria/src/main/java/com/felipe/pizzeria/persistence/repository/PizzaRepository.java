package com.felipe.pizzeria.persistence.repository;

import com.felipe.pizzeria.persistence.entity.PizzaEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface PizzaRepository extends ListCrudRepository <PizzaEntity, Integer>{


}
