package br.medtec.utils;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.Getter;

import java.util.List;

@Getter
public class EntityUtils {
    @Inject
    EntityManager manager;

    public void persist(Object object){
        getManager().persist(object);
    }

    public void remove(Object object){
        getManager().remove(object);
    }

    public <T> Object merge(T object){
        return getManager().merge(object);
    }

    public <T> Object findByOid(Class<T> object, String oid){
        return getManager().find(object, oid);
    }
}
