package yandex.practicum.stealth.explore.server.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yandex.practicum.stealth.explore.server.event.model.Event;
import yandex.practicum.stealth.explore.server.event.service.EventService;
import yandex.practicum.stealth.explore.server.exception.ConditionsNotMetException;
import yandex.practicum.stealth.explore.server.exception.CustomEntityNotFoundException;
import yandex.practicum.stealth.explore.server.exception.RequestWithEnErrorException;
import yandex.practicum.stealth.explore.server.request.dao.RequestRepository;
import yandex.practicum.stealth.explore.server.request.dto.ParticipationDtoMapper;
import yandex.practicum.stealth.explore.server.request.dto.ParticipationRequestDto;
import yandex.practicum.stealth.explore.server.request.model.ParticipationRequest;
import yandex.practicum.stealth.explore.server.user.model.User;
import yandex.practicum.stealth.explore.server.user.service.UserService;
import yandex.practicum.stealth.explore.server.util.EventState;
import yandex.practicum.stealth.explore.server.util.ParticipationStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static yandex.practicum.stealth.explore.server.util.ParticipationStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestServiceImpl implements RequestService {

    private final EventService eventService;
    private final UserService userService;
    private final RequestRepository requestRepository;

    private final ParticipationDtoMapper participationDtoMapper;

    @Override
    public List<ParticipationRequestDto> getUserEventRequests(Long userId, Long eventId) {
        userService.getUserById(userId);
        eventService.findEventById(eventId);
        List<ParticipationRequest> requests = requestRepository.findAllByEventId(eventId);
        System.out.println(requests);
        return requestRepository.findAllByEventId(eventId).stream()
                .map(participationDtoMapper::requestToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto confirmEventRequest(Long userId, Long eventId, Long reqId) {
        userService.getUserById(userId);
        Event event = eventService.findEventById(eventId);
        ParticipationRequest request = getRequest(reqId);
        if (event.getRequestModeration().equals(false) || event.getParticipantLimit().equals(0)) {
            request.setStatus(CONFIRMED);
            return participationDtoMapper.requestToDto(request);
        }
        Long confirmedRequests = countRequests(eventId, CONFIRMED);
        if (confirmedRequests < event.getParticipantLimit()) {
            request.setStatus(CONFIRMED);
            return participationDtoMapper.requestToDto(request);
        }
        request.setStatus(REJECTED);
        requestRepository.save(request);
        return participationDtoMapper.requestToDto(request);
    }

    @Override
    public ParticipationRequestDto rejectEventRequest(Long userId, Long eventId, Long reqId) {
        userService.getUserById(userId);
        eventService.findEventById(eventId);
        ParticipationRequest request = getRequest(reqId);
        request.setStatus(REJECTED);
        requestRepository.save(request);
        return participationDtoMapper.requestToDto(request);
    }

    @Override
    public List<ParticipationRequestDto> getRequestsForParticipate(Long userId) {
        userService.getUserById(userId);
        return requestRepository.findAllByRequester_Id(userId).stream()
                .map(participationDtoMapper::requestToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto addParticipationRequest(Long userId, Long eventId) {
        User user = userService.getUserById(userId);
        Event event = eventService.findEventById(eventId);
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new RequestWithEnErrorException("Participation in unpublished events is denied");
        }
        if (event.getInitiator().getId().equals(userId)) {
            throw new RequestWithEnErrorException(String
                    .format("User id=%s is owner of event id=%s. Participation in your own events is denied",
                            userId, eventId));
        }
        if (requestRepository.findAllByRequester_IdAndEvent_Id(userId, eventId).stream().findFirst().isPresent()) {
            throw new RequestWithEnErrorException(String
                    .format("Request from user id=%s for event id=%s already exist.", userId, eventId));
        }
        if (event.getParticipantLimit() != 0 && countRequests(eventId, CONFIRMED) >= event.getParticipantLimit()) {
            throw new ConditionsNotMetException("The limit on the number of participants has been exceeded.");
        }
        ParticipationRequest request = ParticipationRequest.builder()
                .requester(user)
                .event(event)
                .status(PENDING)
                .created(LocalDateTime.now())
                .build();
        if (!event.getRequestModeration()) {
            request.setStatus(CONFIRMED);
        }
        requestRepository.save(request);
        return participationDtoMapper.requestToDto(request);
    }

    public ParticipationRequestDto rejectUserParticipationRequest(Long userId, Long requestId) {
        userService.getUserById(userId);
        ParticipationRequest request = getRequest(requestId);
        if (!request.getRequester().getId().equals(userId)) {
            throw new RequestWithEnErrorException(String
                    .format("User id=%s is not requester of request id=%s.", userId, requestId));
        }
        request.setStatus(CANCELED);
        requestRepository.save(request);
        return participationDtoMapper.requestToDto(request);
    }

    // privates
    private Long countRequests(Long eventId, ParticipationStatus status) {
        return requestRepository.countConfirmedRequests(eventId, status);
    }

    private ParticipationRequest getRequest(Long reqId) {
        return requestRepository.findById(reqId).orElseThrow(() -> {
            throw new CustomEntityNotFoundException(String.format("Request with id=%s was not found.", reqId));
        });
    }
}
