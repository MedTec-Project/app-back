package br.medtec.utils;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;


public abstract class GenericsService<T> extends EntityUtils {
    Class<T> entityClass;

    public List<T> findAll(){
        return (List<T>) getManager().createQuery("SELECT e FROM" + entityClass.getSimpleName() + " e").getResultList();
    }


}
