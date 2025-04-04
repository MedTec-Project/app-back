package br.medtec.features.user;

import br.medtec.generics.GenericRepository;

public interface UserRepository extends GenericRepository<User> {
    public User findByEmail(String email);

}
