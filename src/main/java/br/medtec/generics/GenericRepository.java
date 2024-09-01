<<<<<<<< HEAD:src/main/java/br/medtec/generics/GenericRepository.java
package br.medtec.generics;
========
package br.medtec.generic;
>>>>>>>> 2cb6c86 (refactor(estrutura): refatorado as estrutura das pasta para minimizar a quantidade de pastas.):src/main/java/br/medtec/generic/GenericRepository.java

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
