package br.medtec.repositories;

import br.medtec.entity.Medicamento;
import br.medtec.entity.Sintoma;
import br.medtec.entity.Usuario;
import br.medtec.utils.ConsultaBuilder;
import br.medtec.utils.GenericRepository;
import br.medtec.utils.UtilString;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class MedicamentoRepository extends GenericRepository<Medicamento> {

    public MedicamentoRepository(){
        super(Medicamento.class);
    }

    public List<Medicamento> findAll(String nome, String oidFabricante, Integer categoriaMedicamento){
        ConsultaBuilder consulta = createConsultaBuilder();
        consulta.select("m");

            if (UtilString.stringValida(nome)) {
                consulta.where("m.nome like :nome")
                        .param("nome", "%" + nome + "%");
            }

            if (UtilString.stringValida(oidFabricante)) {
                consulta.where("m.oidFabricante = :oidFabricante")
                        .param("oidFabricante", oidFabricante);
            }

            if (categoriaMedicamento != null && Medicamento.CategoriaMedicamento.valueOf(categoriaMedicamento) != null) {
                consulta.where("m.categoriaMedicamento = :categoriaMedicamento")
                        .param("categoriaMedicamento", categoriaMedicamento);
            }

            return consulta.executarConsulta();
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
