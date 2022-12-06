package yandex.practicum.stealth.explore.server.event.dto;

import org.springframework.stereotype.Component;
import yandex.practicum.stealth.explore.server.category.model.Category;
import yandex.practicum.stealth.explore.server.event.model.Event;
import yandex.practicum.stealth.explore.server.user.model.User;
import yandex.practicum.stealth.explore.server.util.EventState;

import java.time.LocalDateTime;

import static yandex.practicum.stealth.explore.server.category.dto.CategoryDtoMapper.catToDto;
import static yandex.practicum.stealth.explore.server.user.dto.UserDtoMapper.userToShortDto;

@Component
public class EventDtoMapper {

    public static EventFullDto eventToFullDto(Event event) {
        EventFullDto eventFullDto = EventFullDto.builder()
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .location(event.getLocation())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .build();
        setEventToDto(event, eventFullDto);

        return eventFullDto;
    }

    public static EventShortDto eventToShortDto(Event event) {
        EventShortDto eventShortDto = EventShortDto.builder().build();
        setEventToDto(event, eventShortDto);

        return eventShortDto;
    }

    public static Event newDtoToEvent(NewEventDto newEventDto, Category category, User user) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .category(category)
                .confirmedRequests(0L)
                .createdOn(LocalDateTime.now())
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .initiator(user)
                .location(newEventDto.getLocation())
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .publishedOn(LocalDateTime.now())
                .requestModeration(newEventDto.getRequestModeration())
                .state(EventState.PENDING)
                .title(newEventDto.getTitle())
                .views(0L)
                .build();
    }

    private static void setEventToDto(Event event, AbstractEventDto eventDto) {
        eventDto.setId(event.getId());
        eventDto.setAnnotation(event.getAnnotation());
        eventDto.setCategory(catToDto(event.getCategory()));
        eventDto.setConfirmedRequests(event.getConfirmedRequests());
        eventDto.setEventDate(event.getEventDate());
        eventDto.setInitiator(userToShortDto(event.getInitiator()));
        eventDto.setPaid(event.getPaid());
        eventDto.setTitle(event.getTitle());
        eventDto.setViews(event.getViews());
    }
}
