package br.medtec.features.history;

import br.medtec.generics.JpaGenericRepository;
import br.medtec.utils.QueryBuilder;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class JpaHistoryRepository extends JpaGenericRepository<History> implements HistoryRepository {

    public JpaHistoryRepository() {
        super(History.class);
    }

    @Override
    public List<History> findAll() {
        QueryBuilder queryBuilder = createQueryBuilder();

        queryBuilder.select("h")
                .from("History h");

        queryBuilder.orderBy("h.creationDate DESC");
        return queryBuilder.executeQuery();
    }
}
