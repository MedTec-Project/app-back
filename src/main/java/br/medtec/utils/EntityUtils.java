package br.medtec.utils;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.Getter;

@Getter
public class EntityUtils {

    @Inject
    EntityManager manager;

    public EntityManager getManager(){
        return manager;
    }

    public void setManager(EntityManager manager){
        this.manager = manager;
    }

    public <T> T persist(T object){
        getManager().persist(object);
        return object;
    }

    public <T> void remove(T object){
        getManager().remove(object);
    }

    public <T> Object merge(T object){
        return getManager().merge(object);
    }

}
