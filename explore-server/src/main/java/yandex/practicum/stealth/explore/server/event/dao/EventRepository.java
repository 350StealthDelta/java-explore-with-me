package yandex.practicum.stealth.explore.server.event.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import yandex.practicum.stealth.explore.server.event.model.Event;

import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findEventsByInitiatorId(Long userId, Pageable pageable);

    List<Event> findEventsByCategory_Id(Long catId);

    List<Event> findEventsByIdIn(Set<Long> events);
}
