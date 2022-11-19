package yandex.practicum.stealth.explore.server.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yandex.practicum.stealth.explore.server.compilation.dao.CompilationRepository;
import yandex.practicum.stealth.explore.server.compilation.dto.CompilationDto;
import yandex.practicum.stealth.explore.server.compilation.dto.CompilationDtoMapper;
import yandex.practicum.stealth.explore.server.compilation.dto.NewCompilationDto;
import yandex.practicum.stealth.explore.server.compilation.model.Compilation;
import yandex.practicum.stealth.explore.server.event.dao.EventRepository;
import yandex.practicum.stealth.explore.server.event.model.Event;
import yandex.practicum.stealth.explore.server.exception.ConditionsNotMetException;
import yandex.practicum.stealth.explore.server.exception.CustomEntityNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationDtoMapper dtoMapper;

    private Compilation findCompilationById(Long compId) {
        return compilationRepository.findById(compId).orElseThrow(() -> {
            throw new CustomEntityNotFoundException(String.format("Compilation with id=%s was not found.", compId));
        });
    }

    // "/compilations" endpoints

    @Override
    public CompilationDto getCompilationById(Long compId) {
        return dtoMapper.compToDto(findCompilationById(compId));
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        return compilationRepository.findAllByPinned(pinned).stream()
                .skip(from)
                .limit(size)
                .map(dtoMapper::compToDto)
                .collect(Collectors.toList());
    }

    // "/admin/compilations" endpoints
    @Override
    @Transactional
    public CompilationDto addCompilation(NewCompilationDto body) {
        List<Event> events = eventRepository.findEventsByIdIn(body.getEvents());
        Compilation compilation = dtoMapper.newToCompilation(body, events);
        return dtoMapper.compToDto(compilationRepository.save(compilation));
    }

    @Override
    @Transactional
    public void deleteCompById(Long compId) {
        findCompilationById(compId);
        compilationRepository.deleteById(compId);
    }

    @Override
    @Transactional
    public void deleteEventFromCompById(Long compId, Long eventId) {
        Compilation compilation = findCompilationById(compId);
        eventsInCompExistCheck(compId, eventId, compilation);
        List<Event> eventIds = compilation.getEvents().stream()
                .filter(event -> !event.getId().equals(eventId))
                .collect(Collectors.toList());
        compilation.setEvents(eventIds);
        compilationRepository.save(compilation);
    }

    @Override
    @Transactional
    public void addEventToCompById(Long compId, Long eventId) {
        Compilation compilation = findCompilationById(compId);
        eventsInCompExistCheck(compId, eventId, compilation);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
            throw new CustomEntityNotFoundException(String.format("Event with id=%s was not found.", eventId));
        });
        compilation.getEvents().add(event);
        compilationRepository.save(compilation);
    }

    @Override
    @Transactional
    public void switchPinCompilation(Long compId, Boolean flag) {
        Compilation compilation = findCompilationById(compId);
        compilation.setPinned(flag);
        compilationRepository.save(compilation);
    }

    // private methods
    private static void eventsInCompExistCheck(Long compId, Long eventId, Compilation compilation) {
        if (compilation.getEvents().size() == 0) {
            return;
        }
        if (compilation.getEvents().stream()
                .map(Event::getId)
                .noneMatch(id -> Objects.equals(id, eventId))) {
            throw new ConditionsNotMetException(String
                    .format("Compilation id=%s has not event id=%s", compId, eventId));
        }
    }
}
