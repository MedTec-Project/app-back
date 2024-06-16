package br.medtec.repositories;

import br.medtec.interfaces.GenericRepository;
import br.medtec.utils.ConsultaBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class JpaGenericRepository<T> implements GenericRepository<T> {

    @Inject
    EntityManager em;

    private Class<T> entityClass;

    public JpaGenericRepository(Class<T> clazz) {
        this.entityClass = clazz;
    }

    @Override
    public Optional<T> findByOid(String oid) {
        T entity = em.find(entityClass, oid);
        return entity != null ? Optional.of(entity) : Optional.empty();
    }

    @Override
    public List<T> findAll() {
        TypedQuery<T> query = em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass);
        return query.getResultList();
    }

    @Override
    public void save(T entity) {
        em.persist(entity);
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
        }
    }

    @Override
    public void delete(T entity) {
        em.remove(entity);
    }

    @Override
    public List<T> findByAttribute(String atributeName, Object value) {
        TypedQuery<T> query = em.createQuery(
                "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e." + atributeName + " = :value", entityClass);
        query.setParameter("value", value);
        return query.getResultList();
    }

    public ConsultaBuilder createConsultaBuilder() {
        return new ConsultaBuilder(em);
    }

    public ConsultaBuilder createConsultaNativa() {
        return new ConsultaBuilder(em, true);
    }


}
