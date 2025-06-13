package br.medtec.features.schedule;

import br.medtec.generics.JpaGenericRepository;
import br.medtec.utils.QueryBuilder;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class JpaScheduleRepository extends JpaGenericRepository<Schedule> implements ScheduleRepository {
    public JpaScheduleRepository() {
        super(Schedule.class);
    }

    @Override
    public Schedule findByOid(String oid) {
        QueryBuilder query = createQueryBuilder();
        query.select("s")
                .from("Schedule s")
                .where("s.oid = :oid")
                .param("oid", oid);

        return (Schedule) query.firstResult();
    }


    @Override
    public void delete(Schedule schedule) {
        super.deleteByAttribute("oid", schedule.getOid());
        super.delete(schedule);
    }

}
