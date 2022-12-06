package yandex.practicum.stealth.explore.server.event.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import yandex.practicum.stealth.explore.server.event.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
