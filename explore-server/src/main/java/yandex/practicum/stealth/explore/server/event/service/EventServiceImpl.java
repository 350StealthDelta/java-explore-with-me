package yandex.practicum.stealth.explore.server.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yandex.practicum.stealth.explore.server.category.model.Category;
import yandex.practicum.stealth.explore.server.category.service.CategoryService;
import yandex.practicum.stealth.explore.server.event.dao.EventRepository;
import yandex.practicum.stealth.explore.server.event.dao.EventSpecRepository;
import yandex.practicum.stealth.explore.server.event.dao.LocationRepository;
import yandex.practicum.stealth.explore.server.event.dto.EventDtoMapper;
import yandex.practicum.stealth.explore.server.event.dto.EventFullDto;
import yandex.practicum.stealth.explore.server.event.dto.EventShortDto;
import yandex.practicum.stealth.explore.server.event.dto.NewEventDto;
import yandex.practicum.stealth.explore.server.event.model.Event;
import yandex.practicum.stealth.explore.server.event.model.Location;
import yandex.practicum.stealth.explore.server.exception.ConditionsNotMetException;
import yandex.practicum.stealth.explore.server.exception.CustomEntityNotFoundException;
import yandex.practicum.stealth.explore.server.exception.NotAllowedException;
import yandex.practicum.stealth.explore.server.request.dao.RequestRepository;
import yandex.practicum.stealth.explore.server.user.model.User;
import yandex.practicum.stealth.explore.server.user.service.UserService;
import yandex.practicum.stealth.explore.server.util.EventSort;
import yandex.practicum.stealth.explore.server.util.EventState;
import yandex.practicum.stealth.explore.server.util.ParticipationStatus;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;
import static yandex.practicum.stealth.explore.server.event.dao.EventSpecRepository.*;
import static yandex.practicum.stealth.explore.server.event.dto.EventDtoMapper.eventToFullDto;
import static yandex.practicum.stealth.explore.server.event.dto.EventDtoMapper.newDtoToEvent;
import static yandex.practicum.stealth.explore.server.util.EventState.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final UserService userService;
    private final CategoryService categoryService;
    private final EventRepository eventRepository;
    private final EventSpecRepository specRepository;
    private final RequestRepository requestRepository;
    private final LocationRepository locationRepository;

    // "/events" ENDPOINTS
    @Override
    public List<EventShortDto> getAllEvents(String text, List<Integer> categories, Boolean paid,
                                            LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable,
                                            Integer from, Integer size, EventSort sort) {
        List<Event> events;
        switch (sort) {
            case VIEWS:
                events = specRepository.findAll(where(hasText(text))
                        .and(hasCategories(categories))
                        .and(hasPaid(paid))
                        .and(hasRangeStart(rangeStart))
                        .and(hasRangeEnd(rangeEnd))
                        .and(hasAvailable(onlyAvailable)));
                return events.stream()
                        .sorted(Comparator.comparing(Event::getViews))
                        .skip(from)
                        .limit(size)
                        .map(EventDtoMapper::eventToShortDto)
                        .collect(Collectors.toList());

            case EVENT_DATE:
                events = specRepository.findAll(where(hasText(text))
                        .and(hasCategories(categories))
                        .and(hasPaid(paid))
                        .and(hasRangeStart(rangeStart))
                        .and(hasRangeEnd(rangeEnd))
                        .and(hasAvailable(onlyAvailable)));
                return events.stream()
                        .sorted(Comparator.comparing(Event::getEventDate))
                        .skip(from)
                        .limit(size)
                        .map(EventDtoMapper::eventToShortDto)
                        .collect(Collectors.toList());
            default:
                events = specRepository.findAll(where(hasText(text))
                        .and(hasCategories(categories))
                        .and(hasPaid(paid))
                        .and(hasRangeStart(rangeStart))
                        .and(hasRangeEnd(rangeEnd))
                        .and(hasAvailable(onlyAvailable)));
                return events.stream()
                        .sorted(Comparator.comparing(Event::getId))
                        .skip(from)
                        .limit(size)
                        .map(EventDtoMapper::eventToShortDto)
                        .collect(Collectors.toList());
        }
    }

    @Override
    public EventFullDto getEventById(Long id) {
        Event event = findEventById(id);
        if (!event.getState().equals(PUBLISHED)) {
            throw new ConditionsNotMetException(String
                    .format("Event id=%s is not published.", id));
        }
        Long requestCounts = requestRepository.countConfirmedRequests(id, ParticipationStatus.CONFIRMED);
        event.setConfirmedRequests(requestCounts);
        EventFullDto eventFullDto = eventToFullDto(findEventById(id));
        event.addView();
        eventRepository.save(event);
        return eventFullDto;
    }

    // "/users/{userId}/events" ENDPOINTS
    @Override
    public List<EventShortDto> getUserEvents(Long userId, Integer from, Integer size) {
        userService.getUserById(userId); // проверка существования пользователя
        return eventRepository.findEventsByInitiatorId(userId).stream()
                .skip(from)
                .limit(size)
                .map(EventDtoMapper::eventToShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto updateUserEvent(Long userId, NewEventDto eventDto) {
        userService.getUserById(userId);
        Event event = findEventById(eventDto.getEventId());
        return eventToFullDto(updateEventData(event, eventDto));
    }

    @Override
    @Transactional
    public EventFullDto addUserEvent(Long userId, NewEventDto eventDto) {
        User user = userService.getUserById(userId);
        Category category = categoryService.findCategoryById(eventDto.getCategory());
        Location location = Location.builder()
                .lat(eventDto.getLocation().getLat())
                .lon(eventDto.getLocation().getLon())
                .build();
        eventDto.setLocation(locationRepository.save(location));
        Event event = eventRepository.save(newDtoToEvent(eventDto, category, user));
        return eventToFullDto(event);
    }

    @Override
    public EventFullDto getUserEventById(Long userId, Long eventId) {
        userService.getUserById(userId);
        Event event = findEventById(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new NotAllowedException(String
                    .format("User with id=%s is not owner of event with id=%s", userId, eventId));
        }
        return eventToFullDto(findEventById(eventId));
    }

    @Override
    @Transactional
    public EventFullDto updateUserEventById(Long userId, Long eventId) {
        userService.getUserById(userId);
        Event event = findEventById(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw new NotAllowedException(String
                    .format("User with id=%s is not owner of event with id=%s", userId, eventId));
        }
        if (!event.getState().equals(PUBLISHED)) {
            event.setState(event.getState().equals(PENDING) ? CANCELED : PENDING);
        } else {
            throw new NotAllowedException("Only pending or canceled events can be changed");
        }
        return eventToFullDto(event);
    }

    // "/admin/events" ENDPOINTS
    @Override
    public List<EventFullDto> searchEvents(List<Long> users, List<EventState> states, List<Integer> categories,
                                           LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                           Integer from, Integer size) {
        if (users != null || states != null || categories != null || rangeStart != null || rangeEnd != null) {
            List<Event> events = specRepository.findAll(where(hasUsers(users)).and(hasStates(states))
                    .and(hasCategories(categories)).and(hasRangeStart(rangeStart)).and(hasRangeEnd(rangeEnd)));
            return events.stream()
                    .skip(from)
                    .limit(size)
                    .map(EventDtoMapper::eventToFullDto)
                    .collect(Collectors.toList());
        } else {
            List<Event> events = eventRepository.findAll();
            return events.stream()
                    .skip(from)
                    .limit(size)
                    .map(EventDtoMapper::eventToFullDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    @Transactional
    public EventFullDto updateEventByAdmin(Long eventId, NewEventDto body) {
        Event event = findEventById(eventId);
        event.setAnnotation(body.getAnnotation() != null ?
                body.getAnnotation() : event.getAnnotation());
        event.setCategory(body.getCategory() != null ?
                categoryService.findCategoryById(body.getCategory()) : event.getCategory());
        event.setDescription(body.getDescription() != null ?
                body.getDescription() : event.getDescription());
        event.setEventDate(body.getEventDate() != null ?
                body.getEventDate() : event.getEventDate());
        event.setLocation(body.getLocation() != null ?
                body.getLocation() : event.getLocation());
        event.setPaid(body.getPaid() != null ?
                body.getPaid() : event.getPaid());
        event.setParticipantLimit(body.getParticipantLimit() != null ?
                body.getParticipantLimit() : event.getParticipantLimit());
        event.setRequestModeration(body.getRequestModeration() != null ?
                body.getRequestModeration() : event.getRequestModeration());
        event.setTitle(body.getTitle() != null ?
                body.getTitle() : event.getTitle());
        return eventToFullDto(eventRepository.save(event));
    }

    @Override
    @Transactional
    public EventFullDto publishingEventByAdmin(Long eventId) {
        Event event = findEventById(eventId);
        if (event.getEventDate().minusHours(1).isBefore(LocalDateTime.now())) {
            throw new ConditionsNotMetException(String
                    .format("Event date of event '%s' with id=%s is too late ", event.getTitle(), eventId));
        }
        event.setState(PUBLISHED);
        eventRepository.save(event);
        return eventToFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto cancelingEventByAdmin(Long eventId) {
        Event event = findEventById(eventId);
        event.setState(CANCELED);
        eventRepository.save(event);
        return eventToFullDto(event);
    }

    @Override
    public Event findEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> {
            throw new CustomEntityNotFoundException(String.format("Event with id=%s was not found.", eventId));
        });
    }

    // private methods
    private Event updateEventData(Event event, NewEventDto dto) {
        return Event.builder()
                .annotation(dto.getAnnotation() != null ?
                        dto.getAnnotation() : event.getAnnotation())
                .category(dto.getCategory() != null ?
                        categoryService.findCategoryById(dto.getCategory()) : event.getCategory())
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(dto.getDescription() != null ?
                        dto.getDescription() : event.getDescription())
                .eventDate(dto.getEventDate() != null ?
                        dto.getEventDate() :
                        event.getEventDate())
                .initiator(event.getInitiator())
                .location(dto.getLocation() != null ?
                        dto.getLocation() : event.getLocation())
                .paid(dto.getPaid() != null ?
                        dto.getPaid() : event.getPaid())
                .participantLimit(dto.getParticipantLimit() != null ?
                        dto.getParticipantLimit() : event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(dto.getRequestModeration() != null ?
                        dto.getRequestModeration() : event.getRequestModeration())
                .state(event.getState())
                .title(dto.getTitle() != null ?
                        dto.getTitle() : event.getTitle())
                .views(event.getViews())
                .build();
    }
}
