package br.medtec.features.schedule;

import br.medtec.generics.JpaGenericRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JpaScheduleRepository extends JpaGenericRepository<Schedule> implements ScheduleRepository {
    public JpaScheduleRepository() {
        super(Schedule.class);
    }
}
