package yandex.practicum.stealth.explore.server.event.service;

import yandex.practicum.stealth.explore.server.event.dto.EventFullDto;
import yandex.practicum.stealth.explore.server.event.dto.EventShortDto;
import yandex.practicum.stealth.explore.server.event.dto.NewEventDto;
import yandex.practicum.stealth.explore.server.event.model.Event;
import yandex.practicum.stealth.explore.server.util.EventSort;
import yandex.practicum.stealth.explore.server.util.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    // "/events" ENDPOINTS
    List<EventShortDto> getAllEvents(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                                     LocalDateTime rangeEnd, Boolean onlyAvailable, Integer from, Integer size,
                                     EventSort sort);

    EventFullDto getEventById(Long id);

    // "/users/{userId}/events" ENDPOINTS
    Event findEventById(Long eventId);

    List<EventShortDto> getUserEvents(Long userId, Integer from, Integer size);

    EventFullDto updateUserEvent(Long userId, NewEventDto eventShortDto);

    EventFullDto addUserEvent(Long userId, NewEventDto eventDto);

    EventFullDto getUserEventById(Long userId, Long eventId);

    EventFullDto updateUserEventById(Long userId, Long eventId);

    List<EventFullDto> searchEvents(List<Long> users, List<EventState> states, List<Integer> categories,
                                    LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                    Integer from, Integer size);

    EventFullDto updateEventByAdmin(Long eventId, NewEventDto body);

    EventFullDto publishingEventByAdmin(Long eventId);

    EventFullDto cancelingEventByAdmin(Long eventId);
}
