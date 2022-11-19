package yandex.practicum.stealth.explore.server.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yandex.practicum.stealth.explore.server.event.dto.EventFullDto;
import yandex.practicum.stealth.explore.server.event.dto.EventShortDto;
import yandex.practicum.stealth.explore.server.event.dto.NewEventDto;
import yandex.practicum.stealth.explore.server.event.service.EventService;
import yandex.practicum.stealth.explore.server.request.RequestService;
import yandex.practicum.stealth.explore.server.request.dto.ParticipationRequestDto;
import yandex.practicum.stealth.explore.server.util.OnCreate;
import yandex.practicum.stealth.explore.server.util.OnUpdate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    private final EventService eventService;
    private final RequestService requestService;

    // events

    @GetMapping("/events")
    public List<EventShortDto> getUserEvents(@PathVariable Long userId,
                                             @RequestParam(defaultValue = "0") Integer from,
                                             @RequestParam(defaultValue = "10") Integer size,
                                             HttpServletRequest request) {
        log.info("=== Call 'GET:{}' with 'userId': {}, 'from': {}, 'size': {}",
                request.getRequestURI(), userId, from, size);
        return eventService.getUserEvents(userId, from, size);
    }

    @PatchMapping("/events")
    @Validated({OnUpdate.class})
    public EventFullDto updateUserEvent(@PathVariable Long userId,
                                        @RequestBody @Valid NewEventDto event,
                                        HttpServletRequest request) {
        log.info("=== Call 'PATCH:{}' with 'userId': {}, 'event': {}",
                request.getRequestURI(), userId, event);
        EventFullDto eventFullDto = eventService.updateUserEvent(userId, event);
        log.info("=== Event updated: {}", eventFullDto);
        return eventFullDto;
    }

    @PostMapping("/events")
    @Validated({OnCreate.class})
    public EventFullDto addUserEvent(@PathVariable Long userId,
                                     @RequestBody @Valid NewEventDto event,
                                     HttpServletRequest request) {
        log.info("=== Call 'POST:{}' with 'userId': {}, 'event': {}",
                request.getRequestURI(), userId, event);
        return eventService.addUserEvent(userId, event);
    }

    @GetMapping("/events/{eventId}")
    public EventFullDto getUserEventById(@PathVariable Long userId,
                                         @PathVariable Long eventId,
                                         HttpServletRequest request) {
        log.info("=== Call 'GET:{}' with 'userId': {}, 'eventId': {}",
                request.getRequestURI(), userId, eventId);
        return eventService.getUserEventById(userId, eventId);
    }

    @PatchMapping("/events/{eventId}")
    public EventFullDto updateUserEventById(@PathVariable Long userId,
                                            @PathVariable Long eventId,
                                            HttpServletRequest request) {
        log.info("=== Call 'PATCH:{}' with 'userId': {}, 'eventId': {}",
                request.getRequestURI(), userId, eventId);
        return eventService.updateUserEventById(userId, eventId);
    }

    // requests

    @GetMapping("/events/{eventId}/requests")
    public List<ParticipationRequestDto> getUserEventsRequests(@PathVariable Long userId,
                                                               @PathVariable Long eventId,
                                                               HttpServletRequest request) {
        log.info("=== Call 'GET:{}' with 'userId': {}, 'eventId': {}",
                request.getRequestURI(), userId, eventId);
        return requestService.getUserEventRequests(userId, eventId);
    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto updateUserEventRequestConfirm(@PathVariable Long userId,
                                                                 @PathVariable Long eventId,
                                                                 @PathVariable Long reqId,
                                                                 HttpServletRequest request) {
        log.info("=== Call 'PATCH:{}' with 'userId': {}, 'eventId': {}, 'reqId': {}",
                request.getRequestURI(), userId, eventId, reqId);
        return requestService.confirmEventRequest(userId, eventId, reqId);
    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto updateUserEventRequestReject(@PathVariable Long userId,
                                                                @PathVariable Long eventId,
                                                                @PathVariable Long reqId,
                                                                HttpServletRequest request) {
        log.info("=== Call 'PATCH:{}' with 'userId': {}, 'eventId': {}, 'reqId': {}",
                request.getRequestURI(), userId, eventId, reqId);
        return requestService.rejectEventRequest(userId, eventId, reqId);
    }

    @GetMapping("/requests")
    public List<ParticipationRequestDto> getUserRequests(@PathVariable Long userId,
                                                         HttpServletRequest request) {
        log.info("=== Call 'GET:{}' with 'userId': {}",
                request.getRequestURI(), userId);
        return requestService.getRequestsForParticipate(userId);
    }

    @PostMapping("/requests")
    public ParticipationRequestDto addUserRequest(@PathVariable Long userId,
                                                  @RequestParam(name = "eventId") Long eventId,
                                                  HttpServletRequest request) {
        log.info("=== Call 'POST:{}' with 'userId': {}, 'eventId': {}",
                request.getRequestURI(), userId, eventId);
        return requestService.addParticipationRequest(userId, eventId);
    }

    @PatchMapping("/requests/{requestId}/cancel")
    public ParticipationRequestDto updateUserRequest(@PathVariable Long userId,
                                                     @PathVariable Long requestId,
                                                     HttpServletRequest request) {
        log.info("=== Call 'PATCH:{}' with 'userId': {}, 'requestId': {}",
                request.getRequestURI(), userId, requestId);
        return requestService.rejectUserParticipationRequest(userId, requestId);
    }
}
