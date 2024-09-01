package br.medtec.medico;

import br.medtec.generic.JpaGenericRepository;
import br.medtec.utils.ConsultaBuilder;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JpaMedicoRepository extends JpaGenericRepository<Medico> implements MedicoRepository {

    public JpaMedicoRepository() { super(Medico.class); }

    public Boolean existsByCrm(String crm) {
        return existsByAttribute("crm", crm);
    }

    public Medico findByOid(String oid) {
        ConsultaBuilder consulta = createConsultaBuilder();
        consulta.select("m")
                .from("medico m")
                .from("LEFT JOIN FETCH usuario u")
                .from("LEFT JOIN FETCH pacientes p")
                .where("m.oid = :oid")
                .param("oid", oid);

        return (Medico) consulta.primeiroRegistro();
    }

}
