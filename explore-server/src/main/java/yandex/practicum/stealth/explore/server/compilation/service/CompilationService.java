package yandex.practicum.stealth.explore.server.compilation.service;

import yandex.practicum.stealth.explore.server.compilation.dto.CompilationDto;
import yandex.practicum.stealth.explore.server.compilation.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {

    CompilationDto getById(Long compId);

    List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size);

    CompilationDto add(NewCompilationDto body);

    void deleteById(Long compId);

    void deleteEventFromCompById(Long compId, Long eventId);

    void addEventToCompById(Long compId, Long eventId);

    void switchPin(Long compId, Boolean flag);
}
