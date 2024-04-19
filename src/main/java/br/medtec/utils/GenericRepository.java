package br.medtec.utils;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public abstract class GenericRepository<T> extends EntityUtils {

    @Inject
    EntityManager entityManager;

    Class<T> clazz;
    protected GenericRepository() {
        this.clazz = null;
    }

    public GenericRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    public ConsultaBuilder createConsultaBuilder() {
        return new ConsultaBuilder(entityManager);
    }

}
