package com.felipe.pizzeria.persistence.repository;

import com.felipe.pizzeria.persistence.entity.PizzaEntity;
import com.felipe.pizzeria.service.dto.UpdatePizzaPriceDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PizzaRepository extends ListCrudRepository <PizzaEntity, Integer>{

    List<PizzaEntity> findAllByAvailableTrueOrderByPrice();

    Optional<PizzaEntity> findFirstByAvailableTrueAndNameIgnoreCase(String name);

    List<PizzaEntity> findAllByAvailableTrueAndDescriptionContainingIgnoreCase(String description);
    List<PizzaEntity> findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(String description);

    List<PizzaEntity> findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(double price);
    int countByVeganTrue();

    //metodo que permite actualizar el precio de una pizza desde un sql nativo
    @Query(value =
            "UPDATE pizza"+
            "SET price = :#{#newPizzaPrice.newPrice} "+
            "WHERE id_pizza = :#{#newPizzaPrice.pizzaId} ", nativeQuery = true)
    @Modifying
    void updatePrice(@Param("newPizzaPrice") UpdatePizzaPriceDto newPizzaPrice);



}
