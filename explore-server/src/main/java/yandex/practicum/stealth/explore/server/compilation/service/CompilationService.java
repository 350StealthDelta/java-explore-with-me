package yandex.practicum.stealth.explore.server.compilation.service;

import yandex.practicum.stealth.explore.server.compilation.dto.CompilationDto;
import yandex.practicum.stealth.explore.server.compilation.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {

    CompilationDto getCompilationById(Long compId);

    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto addCompilation(NewCompilationDto body);

    void deleteCompById(Long compId);

    void deleteEventFromCompById(Long compId, Long eventId);

    void addEventToCompById(Long compId, Long eventId);

    void switchPinCompilation(Long compId, Boolean flag);
}
