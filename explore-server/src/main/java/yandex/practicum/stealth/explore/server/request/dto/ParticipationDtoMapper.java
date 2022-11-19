package yandex.practicum.stealth.explore.server.request.dto;

import org.springframework.stereotype.Component;
import yandex.practicum.stealth.explore.server.event.model.Event;
import yandex.practicum.stealth.explore.server.request.model.ParticipationRequest;
import yandex.practicum.stealth.explore.server.user.model.User;

@Component
public class ParticipationDtoMapper {

    public ParticipationRequestDto requestToDto(ParticipationRequest request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .created(request.getCreated())
                .build();
    }

    public ParticipationRequest dtoToRequest(ParticipationRequestDto dto, User user, Event event) {
        return ParticipationRequest.builder()
                .id(dto.getId())
                .event(event)
                .requester(user)
                .status(dto.getStatus())
                .created(dto.getCreated())
                .build();
    }
}
