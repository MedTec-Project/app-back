package br.medtec.generics;

import br.medtec.exceptions.MEDExecption;
import br.medtec.utils.QueryBuilder;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class JpaGenericRepository<T> implements GenericRepository<T> {

    @Inject
    EntityManager em;

    private final Class<T> entityClass;

    public JpaGenericRepository(Class<T> clazz) {
        this.entityClass = clazz;
    }

    @Override
    public T findByOid(String oid) {
        T entity = em.find(entityClass, oid);
        if (entity == null) {
            throw new MEDExecption(entityClass.getSimpleName() + " não encontrado(a)");
        }
        return entity;
    }

    @Override
    public List<T> findAll() {
        TypedQuery<T> query = em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass);
        return query.getResultList();
    }

    @Override
    public T save(T entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        return em.merge(entity);
    }

    @Override
    public void deleteByOid(String oid) {
        T entity = em.find(entityClass, oid);
        if (entity != null) {
            em.remove(entity);
        } else {
            throw new MEDExecption(entityClass.getSimpleName() + " não encontrado(a)");
        }
    }

    @Override
    public void delete(T entity) {
        em.remove(entity);
    }

    @Override
    public void deleteByAttribute(String atributeName, Object value) {
        em.createQuery("DELETE FROM " + entityClass.getSimpleName() + " e WHERE e." + atributeName + " = :value")
                .setParameter("value", value)
                .executeUpdate();
        em.flush();
    }

    @Override
    public List<T> findByAttribute(String atributeName, Object value) {
        TypedQuery<T> query = em.createQuery(
                "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e." + atributeName + " = :value", entityClass);
        query.setParameter("value", value);
        return query.getResultList();
    }

    @Override
    public boolean existsByAttribute(String atributeName, Object value) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e WHERE e." + atributeName + " = :value", Long.class);
        query.setParameter("value", value);
        return query.getSingleResult() > 0;
    }

    public QueryBuilder createQueryBuilder() {
        return new QueryBuilder(em);
    }

    public QueryBuilder createConsultaNativa() {
        return new QueryBuilder(em, true);
    }


}
