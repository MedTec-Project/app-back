package br.medtec.medicamento;

import br.medtec.entity.Sintoma;
import br.medtec.repositories.JpaGenericRepository;
import br.medtec.utils.ConsultaBuilder;
import br.medtec.utils.UtilString;

import java.util.List;

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
    public Sintoma findSintomaByOid(String oid) {
        ConsultaBuilder consulta = createConsultaBuilder();
        return (Sintoma) consulta.select("s")
                .from("Sintoma s")
                .where("s.oid = :oid")
                .param("oid", oid)
                .primeiroRegistro();
    }
}
