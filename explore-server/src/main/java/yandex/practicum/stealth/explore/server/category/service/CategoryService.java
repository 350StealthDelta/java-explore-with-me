package yandex.practicum.stealth.explore.server.category.service;

import yandex.practicum.stealth.explore.server.category.dto.CategoryDto;
import yandex.practicum.stealth.explore.server.category.dto.NewCategoryDto;
import yandex.practicum.stealth.explore.server.category.model.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategoryById(Long catId);

    Category findCategoryById(Long catId);

    CategoryDto addCategory(NewCategoryDto body);

    CategoryDto updateCategory(CategoryDto body);

    void deleteCategoryById(Long catId);
}
