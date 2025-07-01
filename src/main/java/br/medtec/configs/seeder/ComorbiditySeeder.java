package br.medtec.configs.seeder;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class ComorbiditySeeder implements Seeder {

    @Inject
    EntityManager em;

    @Override
    @Transactional
    public void run() {
        em.createNativeQuery("INSERT INTO comorbidity_type (oid, oid_user_creation, creation_date, name, icon, color) VALUES ('comorbidity-type-001', 'user', CURRENT_TIMESTAMP, 'Doenças Respiratórias', 'air', '#80D0FF')")
                .executeUpdate();
        em.createNativeQuery("INSERT INTO comorbidity_type (oid, oid_user_creation, creation_date, name, icon, color) VALUES ('comorbidity-type-002', 'user', CURRENT_TIMESTAMP, 'Doenças Cardiovasculares', 'heart', '#E74C3C')")
                .executeUpdate();
        em.createNativeQuery("INSERT INTO comorbidity_type (oid, oid_user_creation, creation_date, name, icon, color) VALUES ('comorbidity-type-003', 'user', CURRENT_TIMESTAMP, 'Doenças Neurológicas', 'brain', '#CA4987')")
                .executeUpdate();
        em.createNativeQuery("INSERT INTO comorbidity_type (oid, oid_user_creation, creation_date, name, icon, color) VALUES ('comorbidity-type-004', 'user', CURRENT_TIMESTAMP, 'Doenças Metabólicas', 'candy', '#E67E22')")
                .executeUpdate();
        em.createNativeQuery("INSERT INTO comorbidity_type (oid, oid_user_creation, creation_date, name, icon, color) VALUES ('comorbidity-type-005', 'user', CURRENT_TIMESTAMP, 'Doenças Infecciosas', 'virus', '#2ECC71')")
                .executeUpdate();
        em.createNativeQuery("INSERT INTO comorbidity_type (oid, oid_user_creation, creation_date, name, icon, color) VALUES ('comorbidity-type-006', 'user', CURRENT_TIMESTAMP, 'Doenças Renais', 'water', '#3498DB')")
                .executeUpdate();
    }

    @Override
    public int getOrder() {
        return 1;
    }


}
