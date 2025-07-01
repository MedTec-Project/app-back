package br.medtec.features.doctor;

import br.medtec.generics.JpaGenericRepository;
import br.medtec.utils.QueryBuilder;
import br.medtec.utils.UserSession;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class JpaDoctorRepository extends JpaGenericRepository<Doctor> implements DoctorRepository {

    public JpaDoctorRepository() {
        super(Doctor.class);
    }

    public Boolean existsByCrm(String crm) {
        return existsByAttribute("crm", crm);
    }

    @Override
    public List<DoctorDTO> findAllDoctors() {
        QueryBuilder query = createConsultaNativa();
        query.transformDTO(DoctorDTO.class)
                .select("d.oid, p.name, d.crm, d.specialty")
                .from("doctor d")
                .from("JOIN person p ON d.oid = p.oid")
                .where("p.oid_user_creation in (:oidUser, 'user', 'admin')")
                .param("oidUser", UserSession.getOidUser())
                .orderBy("p.name");

        return query.executeQuery();
    }
}
