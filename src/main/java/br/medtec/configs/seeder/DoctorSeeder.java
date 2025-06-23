package br.medtec.configs.seeder;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DoctorSeeder implements Seeder {

    @Inject
    EntityManager em;

    @Override
    @Transactional
    public void run() {
        em.createNativeQuery("INSERT INTO doctor (oid, crm, oid_user_creation, creation_date, version) VALUES ('person-001', 'CRM123456', 'admin-user', NOW(), 0)")
                .executeUpdate();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
