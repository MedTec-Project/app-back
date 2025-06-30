package br.medtec.features.comorbidity;

import br.medtec.generics.JpaGenericRepository;
import br.medtec.utils.QueryBuilder;
import br.medtec.utils.UserSession;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class JpaComorbidityRepository extends JpaGenericRepository<Comorbidity> implements ComorbidityRepository {

    public JpaComorbidityRepository() {
        super(Comorbidity.class);
    }

    @Override
    public List<ComorbidityDTO> findAllByUser() {
        QueryBuilder query = createConsultaNativa();
        query.transformDTO(ComorbidityDTO.class);
        query.select("c.oid, c.name, c.description, ct.oid, ct.name, ct.color, ct.icon");
        query.from("comorbidity c");
        query.from("JOIN comorbidity_type ct ON c.oid_comorbidity_type = ct.oid");
        query.where("c.oid_user_creation = :oidUser");
        query.param("oidUser", UserSession.getOidUser());
        query.orderBy("c.name");
        return query.executeQuery();
    }

    @Override
    public List<ComorbidityType> findAllTypes() {
        QueryBuilder query = createQueryBuilder();
        query.select("ct");
        query.from("ComorbidityType ct");
        query.orderBy("ct.name");
        return query.executeQuery();
    }
}
