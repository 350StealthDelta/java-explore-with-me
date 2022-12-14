package yandex.practicum.stealth.explore.server.category.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class CategoryDto {

    @NotNull
    private Long id;

    @NotNull
    private String name;
}
