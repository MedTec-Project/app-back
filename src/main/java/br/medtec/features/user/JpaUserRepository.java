package br.medtec.features.user;

import br.medtec.generics.JpaGenericRepository;
import br.medtec.utils.QueryBuilder;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JpaUserRepository extends JpaGenericRepository<User> implements UserRepository {

    public JpaUserRepository() {
        super(User.class);
    }

    public User findByEmail(String email) {
        QueryBuilder query = createQueryBuilder();

        query.select("u")
                .from("User u")
                .where("u.email = :email")
                .param("email", email);

        return (User) query.firstResult();
    }
}
