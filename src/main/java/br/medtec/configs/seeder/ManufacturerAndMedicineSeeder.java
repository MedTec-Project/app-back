package br.medtec.configs.seeder;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.stream.IntStream;

@ApplicationScoped
public class ManufacturerAndMedicineSeeder implements Seeder {

    @Inject
    EntityManager em;

    @Transactional
    public void run() {
        em.createNativeQuery("INSERT INTO manufacturer (oid, name, oid_user_creation, creation_date, version) VALUES ('fabric-001', 'Fabricante Exemplo', 'admin-user', NOW(), 0)")
                .executeUpdate();

        IntStream.range(0, 10).forEach(i ->
                em.createNativeQuery("INSERT INTO medicine (oid, name, oid_manufacturer, dosage, dosage_type, description, medicine_category, pharmaceutical_form, content, registration_number, image_path, oid_user_creation, creation_date, oid_user_update, update_date, version) VALUES (:oid, :name, 'fabric-001', :dosage, 0, :desc, :cat, :form, :content, :reg, NULL, 'admin-user', NOW(), NULL, NULL, 0)")
                        .setParameter("oid", "med-00" + i)
                        .setParameter("name", "Medicamento" + i)
                        .setParameter("dosage", 100 + (i * 10))
                        .setParameter("desc", "Descrição " + i)
                        .setParameter("cat", i % 3)
                        .setParameter("form", i % 4)
                        .setParameter("content", 10 + i)
                        .setParameter("reg", "REG0000" + i)
                        .executeUpdate()
        );
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
