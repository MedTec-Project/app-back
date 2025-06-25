package br.medtec.features.calendar;

import br.medtec.generics.JpaGenericRepository;
import br.medtec.utils.QueryBuilder;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class JpaEventRepository extends JpaGenericRepository<Event> implements EventRepository {

    public JpaEventRepository() {
        super(Event.class);
    }

    public List<RegisterEventDTO> findAllEvents() {
        QueryBuilder queryBuilder = createConsultaNativa();
        queryBuilder.transformDTO(RegisterEventDTO.class);
        queryBuilder.select("e.oid, e.title, e.date, e.description")
                .from("event e");

        return queryBuilder.executeQuery();
    }
}
