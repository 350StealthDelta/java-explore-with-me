package yandex.practicum.stealth.explore.server.category.dto;

import org.springframework.stereotype.Component;
import yandex.practicum.stealth.explore.server.category.model.Category;

@Component
public class CategoryDtoMapper {

    public static CategoryDto catToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category newDtoToCat(NewCategoryDto dto) {
        return Category.builder()
                .name(dto.getName())
                .build();
    }
}
