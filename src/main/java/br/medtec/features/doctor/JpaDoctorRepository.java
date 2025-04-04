package br.medtec.features.doctor;

import br.medtec.generics.JpaGenericRepository;
import br.medtec.utils.QueryBuilder;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JpaDoctorRepository extends JpaGenericRepository<Doctor> implements DoctorRepository {

    public JpaDoctorRepository() { super(Doctor.class); }

    public Boolean existsByCrm(String crm) {
        return existsByAttribute("crm", crm);
    }

    public Doctor findByOid(String oid) {
        QueryBuilder query = createQueryBuilder();
        query.select("d")
                .from("doctor d")
                .from("LEFT JOIN FETCH user u")
                .from("LEFT JOIN FETCH patients p")
                .where("d.oid = :oid")
                .param("oid", oid);

        return (Doctor) query.firstResult();
    }
}
