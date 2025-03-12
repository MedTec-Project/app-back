package br.medtec.features.medicamento;

import br.medtec.features.symptom.Symptom;
import br.medtec.generics.JpaGenericRepository;
import br.medtec.utils.ConsultaBuilder;
import br.medtec.utils.UtilString;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class JpaMedicamentoRepository extends JpaGenericRepository<Medicamento> implements MedicamentoRepository {

    public JpaMedicamentoRepository() {
        super(Medicamento.class);
    }

    @Override
    public List<Medicamento> findAll(String nome, String oidFabricante, Integer categoriaMedicamento) {
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

        if (Medicamento.CategoriaMedicamento.valueOf(categoriaMedicamento) != null) {
                consulta.where("m.categoriaMedicamento = :categoriaMedicamento")
                        .param("categoriaMedicamento", categoriaMedicamento);
        }

        return consulta.executarConsulta();

    }

    @Override
    public Symptom findSintomaByOid(String oid) {
        ConsultaBuilder consulta = createConsultaBuilder();
        return (Symptom) consulta.select("s")
                .from("Symptom s")
                .where("s.oid = :oid")
                .param("oid", oid)
                .primeiroRegistro();
    }
}
