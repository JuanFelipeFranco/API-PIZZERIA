package com.felipe.pizzeria.service;

import com.felipe.pizzeria.persistence.entity.PizzaEntity;
import com.felipe.pizzeria.persistence.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {

    //para tener consultas dentro de nuestro servicio usamos jdbctemplate.
    //privatefinalJdbcTemplatejdbcTemplate;

    //con
    private final PizzaRepository pizzaRepository;


    //como se anoto como final lo agregamos como constructor de parametro
    @Autowired
    public PizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    //metodo con el que consultamos todas las pizzas que tenemos en la pizzeria
    public List<PizzaEntity> getAll(){
        //jdbc nos permite crear consultas sql desde java y convertir el resultado en clases java.
        //return this.jdbcTemplate.query("SELECT * FROM pizza", new BeanPropertyRowMapper<>(PizzaEntity.class));
        return this.pizzaRepository.findAll();
    }

    public PizzaEntity getPizza(int idPizza){
        return this.pizzaRepository.findById(idPizza).orElse(null);
    }

    public List<PizzaEntity> getAvailablePizza(){
        System.out.println(this.pizzaRepository.countByVeganTrue());
        return this.pizzaRepository.findAllByAvailableTrueOrderByPrice();
    }

    public PizzaEntity getByNamePizza(String name){
        return this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name).orElseThrow(()-> new RuntimeException("La pizza no existe"));
    }

    public List<PizzaEntity> getWith(String ingredient){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(ingredient);
    }

    public List<PizzaEntity> getWithOut(String ingredient){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(ingredient);
    }

    public List<PizzaEntity> getCheapestPizza(double price){
        return this.pizzaRepository.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
    }
    public PizzaEntity save(PizzaEntity pizza){
        return this.pizzaRepository.save(pizza);
    }

    //para actualizar usamos el mismo metodo save pero en este caso vamos a usar el metodo existById que viene por defecto.
    public boolean exists(int idPizza){
        return this.pizzaRepository.existsById(idPizza);
    }

    public void deletePizza(int idPizza){
        this.pizzaRepository.deleteById(idPizza);
    }



}
