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

    public QueryBuilder createQueryBuilder() {  return new QueryBuilder(entityManager); }

    public QueryBuilder createQueryBuilderNativa() { return new QueryBuilder(entityManager, true); }

    public T findByOid(String oid) {
          return entityManager.find(clazz, oid);
    }

}
