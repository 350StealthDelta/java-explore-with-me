package yandex.practicum.stealth.explore.server.event;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface EventStat {

    ResponseEntity<Object> addHitToStatistic(HttpServletRequest request);
}
