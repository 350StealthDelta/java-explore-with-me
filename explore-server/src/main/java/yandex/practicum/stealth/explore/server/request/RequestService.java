package yandex.practicum.stealth.explore.server.request;

import yandex.practicum.stealth.explore.server.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {

    List<ParticipationRequestDto> getUserEventRequests(Long userId, Long eventId);

    ParticipationRequestDto confirmEventRequest(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto rejectEventRequest(Long userId, Long eventId, Long reqId);

    List<ParticipationRequestDto> getRequestsForParticipate(Long userId);

    ParticipationRequestDto addParticipationRequest(Long userId, Long eventId);

    ParticipationRequestDto rejectUserParticipationRequest(Long userId, Long requestId);
}
