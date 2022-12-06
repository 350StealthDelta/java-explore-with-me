package yandex.practicum.stealth.explore.server.request.dto;

import org.springframework.stereotype.Component;
import yandex.practicum.stealth.explore.server.event.model.Event;
import yandex.practicum.stealth.explore.server.request.model.ParticipationRequest;
import yandex.practicum.stealth.explore.server.user.model.User;

import java.time.LocalDateTime;

import static yandex.practicum.stealth.explore.server.util.ParticipationStatus.PENDING;

@Component
public class ParticipationDtoMapper {

    public static ParticipationRequestDto requestToDto(ParticipationRequest request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .created(request.getCreated())
                .build();
    }

    public static ParticipationRequest dtoToRequest(ParticipationRequestDto dto, User user, Event event) {
        return ParticipationRequest.builder()
                .id(dto.getId())
                .event(event)
                .requester(user)
                .status(dto.getStatus())
                .created(dto.getCreated())
                .build();
    }

    public static ParticipationRequest getNewRequest(User user, Event event) {
        return ParticipationRequest.builder()
                .requester(user)
                .event(event)
                .status(PENDING)
                .created(LocalDateTime.now())
                .build();
    }
}
