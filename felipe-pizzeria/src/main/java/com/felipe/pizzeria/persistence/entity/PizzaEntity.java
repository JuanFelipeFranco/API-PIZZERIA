package com.felipe.pizzeria.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "pizza")
@Getter
@Setter
@NoArgsConstructor //constructor sin parametro
public class PizzaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //nos permite que vaya aumentando de a uno
    @Column(name = "id_pizza", nullable = false) //garantizamos que no sea nulo su valor
    private Integer idPizza;

    @Column(nullable = false,length = 30, unique = true) //es unico porque no puede existir dos pizzas con el mismo nombre
    private String name;

    @Column(nullable = false,length = 150)
    private String description;

    @Column(nullable = false, columnDefinition = "Decimal(5,2)")
    private Double price;

    @Column(columnDefinition = "TINYINT")
    private Boolean vegetarian;

    @Column(columnDefinition = "TINYINT")
    private Boolean vegan;

    @Column(columnDefinition = "TINYINT",nullable = false)
    private Boolean available;


}
