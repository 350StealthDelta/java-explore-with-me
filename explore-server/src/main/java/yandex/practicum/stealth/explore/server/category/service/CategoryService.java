package yandex.practicum.stealth.explore.server.category.service;

import yandex.practicum.stealth.explore.server.category.dto.CategoryDto;
import yandex.practicum.stealth.explore.server.category.dto.NewCategoryDto;
import yandex.practicum.stealth.explore.server.category.model.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAll(Integer from, Integer size);

    Category findById(Long catId);

    CategoryDto create(NewCategoryDto body);

    CategoryDto update(CategoryDto body);

    void deleteById(Long catId);
}
