package br.medtec.features.calendar;

import br.medtec.generics.GenericRepository;

import java.util.List;

public interface EventRepository extends GenericRepository<Event> {

    List<RegisterEventDTO> findAllEvents();
}
