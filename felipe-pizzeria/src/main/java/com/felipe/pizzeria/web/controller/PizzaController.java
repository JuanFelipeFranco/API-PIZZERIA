package com.felipe.pizzeria.web.controller;

import com.felipe.pizzeria.persistence.entity.PizzaEntity;
import com.felipe.pizzeria.service.PizzaService;
import com.felipe.pizzeria.service.dto.UpdatePizzaPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {
    private final PizzaService pizzaService;

    @Autowired //inyectamos la dependencia de pizzaservice
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping
    public ResponseEntity<Page<PizzaEntity>> getAll(@RequestParam(defaultValue = "0")int page,
                                                    @RequestParam(defaultValue = "8")int elements){
        return ResponseEntity.ok(this.pizzaService.getAllPizza(page,elements));
    }

    @GetMapping("/{idPizza}")
    public ResponseEntity<PizzaEntity> getPizza(@PathVariable int idPizza){
        return ResponseEntity.ok(this.pizzaService.get(idPizza));
    }

//------------------PAGE AND SORTING-------------------------------------------------
    @GetMapping("/available")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Page<PizzaEntity>> getAvailable(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "8")int elements,
                                                          @RequestParam(defaultValue = "price")String sortBy,
                                                          @RequestParam(defaultValue = "ASC") String sortDirection){
        return ResponseEntity.ok(this.pizzaService.getAvailablePizza(page, elements, sortBy,sortDirection));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PizzaEntity> getByNamePizza(@PathVariable String name){
        return ResponseEntity.ok(this.pizzaService.getByNamePizza(name));
    }

    @GetMapping("/with/{ingredient}")
    public  ResponseEntity<List<PizzaEntity>> getWith(@PathVariable String ingredient){
        return ResponseEntity.ok(this.pizzaService.getWith(ingredient));
    }

    @GetMapping("/without/{ingredient}")
    public  ResponseEntity<List<PizzaEntity>> getWithOut(@PathVariable String ingredient){
        return ResponseEntity.ok(this.pizzaService.getWithOut(ingredient));
    }

    @GetMapping("/cheapest/{price}")
    public ResponseEntity<List<PizzaEntity>> getCheapestPizzas(@PathVariable double price){
        return ResponseEntity.ok(this.pizzaService.getCheapestPizza(price));
    }

    @PostMapping
    public ResponseEntity<PizzaEntity> addPizza(@RequestBody PizzaEntity pizza){
        //si la pizza no existe o el id de la pizza es nulo podemos crearlo
        if (pizza.getIdPizza() == null || !this.pizzaService.exists(pizza.getIdPizza())){
            return ResponseEntity.ok(this.pizzaService.save(pizza));
        }
        //devuelve un error cuando la pizza existe; usamos badrequest para que no se procese la peticion
        return ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<PizzaEntity> updatePizza(@RequestBody PizzaEntity pizza){
        //si la pizza es diferente de null y ya existe.
        if (pizza.getIdPizza() != null && this.pizzaService.exists(pizza.getIdPizza())){
            return ResponseEntity.ok(this.pizzaService.save(pizza));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/price")
    public ResponseEntity<Void> updatePrice(@RequestBody UpdatePizzaPriceDto updatePizzaPriceDto){
        //si la pizza es diferente de null y ya existe.
        if (this.pizzaService.exists(updatePizzaPriceDto.getPizzaId())){
            this.pizzaService.updatePrice(updatePizzaPriceDto);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{idPizza}")
    public ResponseEntity<Void> deletePizza(@PathVariable int idPizza){
        if (this.pizzaService.exists(idPizza)){
            this.pizzaService.deletePizza(idPizza);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
