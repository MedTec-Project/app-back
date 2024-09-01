package br.medtec.generic;

import java.util.List;

public interface GenericRepository<T> {
    T findByOid(String oid);
    List<T> findAll();
    void save(T entity);
    T update(T entity);
    void deleteByOid(String oid);
    void delete(T entity);
    List<T> findByAttribute(String atributeName, Object value);
    boolean existsByAttribute(String atributeName, Object value);
}
