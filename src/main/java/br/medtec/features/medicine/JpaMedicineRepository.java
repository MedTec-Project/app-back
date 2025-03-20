package br.medtec.features.medicine;

import br.medtec.features.symptom.Symptom;
import br.medtec.generics.JpaGenericRepository;
import br.medtec.utils.QueryBuilder;
import br.medtec.utils.StringUtil;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class JpaMedicineRepository extends JpaGenericRepository<Medicine> implements MedicineRepository {

    public JpaMedicineRepository() {
        super(Medicine.class);
    }

    @Override
    public List<Medicine> findAll(String name, String manufacturerOid, Integer medicineCategory) {
        QueryBuilder query = createQueryBuilder();
        query.select("m");

        if (StringUtil.isValidString(name)) {
            query.where("m.name like :name")
                    .param("name", "%" + name + "%");
        }

        if (StringUtil.isValidString(manufacturerOid)) {
            query.where("m.manufacturerOid = :manufacturerOid")
                    .param("manufacturerOid", manufacturerOid);
        }

        if (Medicine.MedicineCategory.valueOf(medicineCategory) != null) {
            query.where("m.medicineCategory = :medicineCategory")
                    .param("medicineCategory", medicineCategory);
        }

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
