package br.medtec.features.medicine;

import br.medtec.features.symptom.Symptom;
import br.medtec.generics.JpaGenericRepository;
import br.medtec.utils.QueryBuilder;
import br.medtec.utils.StringUtil;
import br.medtec.utils.UserSession;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class JpaMedicineRepository extends JpaGenericRepository<Medicine> implements MedicineRepository {

    public JpaMedicineRepository() {
        super(Medicine.class);
    }

    @Override
    public List<MedicineDTO> findAll(String name, String manufacturerOid, String medicineCategory) {
        QueryBuilder query = createConsultaNativa();
        query.transformDTO(MedicineDTO.class)
                .select("m.oid, m.name, m.dosage, m.dosage_type, m.pharmaceutical_form, m.image_path, m.medicine_category, m.content, STRING_AGG(s.name, ', ') as symptoms")
                .from("medicine m")
                .from("LEFT JOIN symptom_medicine sm ON m.oid = sm.medicine_oid")
                .from("LEFT JOIN symptom s ON sm.symptom_oid = s.oid")
                .where("m.oid_user_creation in (:oidUser, 'user', 'admin')")
                .param("oidUser", UserSession.getOidUser());


        if (StringUtil.isValidString(name)) {
            query.where("m.name like :name")
                    .param("name", "%" + name + "%");
        }

        if (StringUtil.isValidString(manufacturerOid)) {
            query.where("m.manufacturerOid = :manufacturerOid")
                    .param("manufacturerOid", manufacturerOid);
        }

        if (StringUtil.isValidString(medicineCategory)) {
            query.where("m.medicineCategory = :medicineCategory")
                    .param("medicineCategory", medicineCategory);
        }

        query.groupBy("m.oid");

        return query.executeQuery();
    }

    @Override
    public Symptom findSymptomByOid(String oid) {
        QueryBuilder query = createQueryBuilder();
        return (Symptom) query.select("s")
                .from("Symptom s")
                .where("s.oid = :oid")
                .param("oid", oid)
                .firstResult();
    }
}
