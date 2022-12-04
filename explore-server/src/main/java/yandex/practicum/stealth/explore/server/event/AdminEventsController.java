package yandex.practicum.stealth.explore.server.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yandex.practicum.stealth.explore.server.event.dto.EventFullDto;
import yandex.practicum.stealth.explore.server.event.dto.NewEventDto;
import yandex.practicum.stealth.explore.server.event.service.EventService;
import yandex.practicum.stealth.explore.server.util.EventState;
import yandex.practicum.stealth.explore.server.util.OnUpdate;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminEventsController {

    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> getAdminEvents(@RequestParam(name = "users", required = false) List<Long> users,
                                             @RequestParam(name = "states", required = false) List<EventState> states,
                                             @RequestParam(name = "categories", required = false)
                                                 List<Integer> categories,
                                             @RequestParam(name = "rangeStart", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                             @RequestParam(name = "rangeEnd", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                             @RequestParam(name = "from", defaultValue = "0") Integer from,
                                             @RequestParam(name = "size", defaultValue = "10") Integer size,
                                             HttpServletRequest request) {
        log.info("=== Call 'GET:{}' with 'users': {}, 'states': {}, 'categories': {}, 'rangeStart': {}, " +
                        "'rangeEnd': {}, 'from': {}, 'size': {}",
                request.getRequestURI(), users, states, categories, rangeStart, rangeEnd, from, size);

        return eventService.searchEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping(path = "/{eventId}")
    public EventFullDto updateAdminEvent(@PathVariable Long eventId,
                                         @RequestBody @Validated({OnUpdate.class}) NewEventDto body,
                                         HttpServletRequest request) {
        log.info("=== Call 'PUT:{}' with 'eventId': {}, 'body': {}",
                request.getRequestURI(), eventId, body);

        return eventService.updateEventByAdmin(eventId, body);
    }

    @PatchMapping(path = "/{eventId}/publish")
    public EventFullDto publicAdminEvent(@PathVariable Long eventId,
                                         HttpServletRequest request) {
        log.info("=== Call 'PATCH:{}' with 'eventId': {}",
                request.getRequestURI(), eventId);

        return eventService.publishingEventByAdmin(eventId);
    }

    @PatchMapping(path = "/{eventId}/reject")
    public EventFullDto rejectAdminEvent(@PathVariable Long eventId,
                                         HttpServletRequest request) {
        log.info("=== Call 'PATCH:{}' with 'eventId': {}",
                request.getRequestURI(), eventId);

        return eventService.cancelingEventByAdmin(eventId);
    }
}
