package com.felipe.pizzeria.service;

import com.felipe.pizzeria.persistence.entity.PizzaEntity;
import com.felipe.pizzeria.persistence.repository.PizzaPagSortRepository;
import com.felipe.pizzeria.persistence.repository.PizzaRepository;
import com.felipe.pizzeria.service.dto.UpdatePizzaPriceDto;
import com.felipe.pizzeria.service.exception.EmailApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class PizzaService {

    //para tener consultas dentro de nuestro servicio usamos jdbctemplate.
    //privatefinalJdbcTemplatejdbcTemplate;

    //con
    private final PizzaRepository pizzaRepository;
    private final PizzaPagSortRepository pizzaPagSortRepository;


    //como se anoto como final lo agregamos como constructor de parametro
    @Autowired
    public PizzaService(PizzaRepository pizzaRepository, PizzaPagSortRepository pizzaPagSortRepository) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaPagSortRepository = pizzaPagSortRepository;
    }

    //metodo con el que consultamos todas las pizzas que tenemos en la pizzeria
    public Page<PizzaEntity> getAllPizza(int page, int elements){
        //jdbc nos permite crear consultas sql desde java y convertir el resultado en clases java.
        //return this.jdbcTemplate.query("SELECT * FROM pizza", new BeanPropertyRowMapper<>(PizzaEntity.class));
        Pageable pageRequest = PageRequest.of(page, elements);
        return this.pizzaPagSortRepository.findAll(pageRequest);
    }


    //-----------------------------paging and Sorting----------------------
   public Page<PizzaEntity> getAvailablePizza(int page, int elements, String sortBy, String sortDirection){
       System.out.println(this.pizzaRepository.countByVeganTrue());

       Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page,elements,sort);
        return this.pizzaPagSortRepository.findByAvailableTrue(pageRequest);
    }

    /*public List<PizzaEntity> getAvailablePizza(){
        return this.pizzaRepository.findAllByAvailableTrueOrderByPrice();
    }*/

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

    public PizzaEntity get(int idPizza){
        return this.pizzaRepository.findById(idPizza).orElse(null);
    }
    public PizzaEntity save(PizzaEntity pizza){
        return this.pizzaRepository.save(pizza);
    }

    public void deletePizza(int idPizza){
        this.pizzaRepository.deleteById(idPizza);
    }

    @Transactional(noRollbackFor = EmailApiException.class, propagation = Propagation.REQUIRED)
    public void updatePrice(UpdatePizzaPriceDto updatePizzaPriceDto){
        this.pizzaRepository.updatePrice(updatePizzaPriceDto);
        this.sendEmaiil();
    }

    private void sendEmaiil(){
        throw new EmailApiException();
    }

    //para actualizar usamos el mismo metodo save pero en este caso vamos a usar el metodo existById que viene por defecto.
    public boolean exists(int idPizza){
        return this.pizzaRepository.existsById(idPizza);
    }

}
