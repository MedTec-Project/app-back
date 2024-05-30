package br.medtec.utils;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;


public abstract class GenericsService<T> extends EntityUtils {

    @Inject
    SecurityContext securityContext;

    Class<T> entityClass;

    public List<T> findAll(){
        return (List<T>) getManager().createQuery("SELECT e FROM" + entityClass.getSimpleName() + " e").getResultList();
    }

    public T findByOid(String oid){
        return (T) getManager().find(entityClass, oid);
    }







}
