package yandex.practicum.stealth.explore.server.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import yandex.practicum.stealth.explore.server.event.dto.EventFullDto;
import yandex.practicum.stealth.explore.server.event.dto.EventShortDto;
import yandex.practicum.stealth.explore.server.event.service.EventService;
import yandex.practicum.stealth.explore.server.util.EventSort;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
@Slf4j
public class EventController {

    private final EventService eventService;
    private final EventStat statClient;

    @GetMapping
    public List<EventShortDto> getAllEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Integer> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false", required = false) Boolean onlyAvailable,
            @RequestParam(required = false, defaultValue = "EVENT_DATE") EventSort sort,
            @RequestParam(defaultValue = "0", required = false) Integer from,
            @RequestParam(defaultValue = "10", required = false) Integer size,
            HttpServletRequest request) {
        log.info("=== Call 'GET:{}' with text: {}, categories: {}, paid: {}, rangeStart: {}," +
                        " rangeEnd: {}, onlyAvailable: {}, sort: {}, from: {}, size: {}",
                request.getRequestURI(), text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        statClient.addHitToStatistic(request);

        return eventService.getAllEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, from, size, sort);
    }

    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable Long id, HttpServletRequest request) {
        log.info("=== Call 'GET:{}' with id: {}", request.getRequestURI(), id);
        EventFullDto eventFullDto = eventService.getEventById(id);
        statClient.addHitToStatistic(request);

        return eventFullDto;
    }
}
