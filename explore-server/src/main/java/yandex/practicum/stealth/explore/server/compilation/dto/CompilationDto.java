package yandex.practicum.stealth.explore.server.compilation.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import yandex.practicum.stealth.explore.server.event.dto.EventShortDto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
@Jacksonized
public class CompilationDto {
    private Long id;
    private Boolean pinned;
    private String title;
    @NotEmpty
    private List<EventShortDto> events;
}
