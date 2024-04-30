package com.felipe.pizzeria.persistence.audit;

import com.felipe.pizzeria.persistence.entity.PizzaEntity;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import org.springframework.util.SerializationUtils;

public class AuditPizzaListener {

    private PizzaEntity currentValue;

    @PostLoad //se ejecuta despues de hacer un select y la informacion sea cargada en un entity
    public void postLoad(PizzaEntity entity){
        System.out.println("POST LOAD");
        this.currentValue = SerializationUtils.clone(entity);
    }

    @PostPersist
    @PostUpdate //actualizacion de una pizza existente
    public void OnPostPersist(PizzaEntity entity){
        System.out.println("POST PERSIST OR UPDATE");
        System.out.println("OLD VALUE: "+ this.currentValue.toString());
        System.out.println("NEW VALUE: " + entity.toString());
    }

    @PreRemove //ese metodo se ejecuta antes de eliminar en la base de datos
    public  void onPreDelete(PizzaEntity entity){
        System.out.println(entity.toString());
    }
}
