package yandex.practicum.stealth.explore.server.category.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class NewCategoryDto {
    @NotBlank
    private String name;
}
