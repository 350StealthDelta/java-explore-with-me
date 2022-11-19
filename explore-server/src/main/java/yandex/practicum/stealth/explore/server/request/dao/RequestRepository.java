package yandex.practicum.stealth.explore.server.request.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yandex.practicum.stealth.explore.server.request.model.ParticipationRequest;
import yandex.practicum.stealth.explore.server.util.ParticipationStatus;

import java.util.List;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {

    @Query("SELECT r " +
            "FROM ParticipationRequest AS r " +
            "WHERE r.event.id = ?1")
    List<ParticipationRequest> findAllByEventId(Long eventId);

    @Query("SELECT count(r) " +
            "FROM ParticipationRequest AS r " +
            "WHERE r.event.id = ?1 " +
            "AND r.status = ?2")
    Long countConfirmedRequests(Long eventId, ParticipationStatus state);

    List<ParticipationRequest> findAllByRequester_Id(Long userId);

    List<ParticipationRequest> findAllByRequester_IdAndEvent_Id(Long userId, Long eventId);
}
