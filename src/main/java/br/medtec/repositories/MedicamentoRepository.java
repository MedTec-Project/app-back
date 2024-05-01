package br.medtec.repositories;

import br.medtec.entity.Medicamento;
import br.medtec.entity.Sintoma;
import br.medtec.entity.Usuario;
import br.medtec.utils.ConsultaBuilder;
import br.medtec.utils.GenericRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class MedicamentoRepository extends GenericRepository<Medicamento> {

    public MedicamentoRepository(){
        super(Medicamento.class);
    }

    public List<Medicamento> findAll(){
        ConsultaBuilder consulta = createConsultaBuilder();
        return consulta.select("m")
                .from("Medicamento m")
                .executarConsulta();
    }

    public Sintoma findSintomaByOid(String oid){
        ConsultaBuilder consulta = createConsultaBuilder();
        return (Sintoma) consulta.select("s")
                .from("Sintoma s")
                .where("s.oid = :oid")
                .param("oid", oid)
                .primeiroRegistro();
    }



}
