package br.medtec.configs.seeder;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.stream.IntStream;

@ApplicationScoped
public class PersonSeeder implements Seeder {

    @Inject
    EntityManager em;

    @Override
    public int getOrder() { return 2; }

    @Transactional
    public void run() {
        IntStream.range(0, 10).forEach(i ->
                em.createNativeQuery("INSERT INTO person (oid, name, last_name, contact_email, phone, cpf, oid_user_creation, creation_date, oid_user_update, update_date, version) VALUES (:oid, :name, :lastName, :email, :phone, :cpf, 'admin-user', NOW(), NULL, NULL, 0)")
                        .setParameter("oid", "person-00" + i)
                        .setParameter("name", "Nome" + i)
                        .setParameter("lastName", "Sobrenome" + i)
                        .setParameter("email", "user" + i + "@medtec.com")
                        .setParameter("phone", "1199999900" + i)
                        .setParameter("cpf", "1234567800" + i)
                        .executeUpdate()
        );
    }
}
