package br.medtec.interfaces;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T> {
    Optional<T> findByOid(String oid);
    List<T> findAll();
    void save(T entity);
    T update(T entity);
    void deleteByOid(String oid);
    void delete(T entity);
    List<T> findByAttribute(String atributeName, Object value);
}
