package yandex.practicum.stealth.explore.stat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yandex.practicum.stealth.explore.stat.model.StatEntity;
import yandex.practicum.stealth.explore.stat.model.ViewEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<StatEntity, Long> {

    @Query("SELECT new yandex.practicum.stealth.explore.stat.model.ViewEntity(s.app, s.uri, count(s.ip)) FROM StatEntity s " +
            "WHERE s.uri = ?1 " +
            "AND s.timestamp >= ?2 " +
            "AND s.timestamp <= ?3 " +
            "GROUP BY s.app, s.uri ")
    List<ViewEntity> findAllHit(String uri, LocalDateTime timeStart, LocalDateTime timeEnd);

    @Query("SELECT new yandex.practicum.stealth.explore.stat.model.ViewEntity(s.app, s.uri, count(distinct s.ip)) FROM StatEntity s " +
            "WHERE s.uri = ?1 " +
            "AND s.timestamp >= ?2 " +
            "AND s.timestamp <= ?3 " +
            "GROUP BY s.app, s.uri ")
    List<ViewEntity> findAllHitsDistinct(String uri, LocalDateTime timeStart, LocalDateTime timeEnd);
}
