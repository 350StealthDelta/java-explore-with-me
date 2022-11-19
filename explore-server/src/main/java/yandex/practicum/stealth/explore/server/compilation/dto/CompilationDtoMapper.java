package yandex.practicum.stealth.explore.server.compilation.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import yandex.practicum.stealth.explore.server.compilation.model.Compilation;
import yandex.practicum.stealth.explore.server.event.dto.EventDtoMapper;
import yandex.practicum.stealth.explore.server.event.model.Event;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationDtoMapper {

    public CompilationDto compToDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(compilation.getEvents().stream()
                        .map(EventDtoMapper::eventToShortDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public Compilation newToCompilation(NewCompilationDto body, List<Event> events) {
        return Compilation.builder()
                .pinned(body.getPinned())
                .title(body.getTitle())
                .events(events)
                .build();
    }
}
