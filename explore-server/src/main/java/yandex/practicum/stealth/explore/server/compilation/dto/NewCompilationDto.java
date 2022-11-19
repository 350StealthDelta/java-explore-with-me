package yandex.practicum.stealth.explore.server.compilation.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
@Jacksonized
public class NewCompilationDto {
    private Set<Long> events;
    @Builder.Default()
    private Boolean pinned = false;
    @NotBlank
    private String title;
}
