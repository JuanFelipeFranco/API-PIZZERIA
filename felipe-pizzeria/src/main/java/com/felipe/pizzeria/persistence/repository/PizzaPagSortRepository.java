package com.felipe.pizzeria.persistence.repository;

import com.felipe.pizzeria.persistence.entity.PizzaEntity;
import org.hibernate.query.Page;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.awt.print.Pageable;

public interface PizzaPagSortRepository extends ListPagingAndSortingRepository <PizzaEntity, Integer> {
//    Page<PizzaEntity> findByAvailableTrue(Pageable pageable);
}
