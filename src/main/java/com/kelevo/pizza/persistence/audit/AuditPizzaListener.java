package com.kelevo.pizza.persistence.audit;

import com.kelevo.pizza.persistence.entity.PizzaEntity;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import org.springframework.util.SerializationUtils;

public class AuditPizzaListener {

    private PizzaEntity currentValue;

    // Metodos que se ejecutan automaticamente al interactuar con las pizzas
    @PostLoad
    public void postLoad(PizzaEntity entity) {
        System.out.println("Post load");
        this.currentValue = SerializationUtils.clone(entity);
    }

    @PostPersist
    @PostUpdate
    public void onPostPersist(PizzaEntity entity) {
        System.out.println("postUpdate / postPersist");
        System.out.println("Old value: " + this.currentValue);
        System.out.println("New value: " + entity.toString());
    }

    @PreRemove
    public void onPredelete(PizzaEntity entity) {
        System.out.println("onPreDelete == " + entity.toString());
    }
}
