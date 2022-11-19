package yandex.practicum.stealth.explore.server.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yandex.practicum.stealth.explore.server.category.dao.CategoryRepository;
import yandex.practicum.stealth.explore.server.category.dto.CategoryDto;
import yandex.practicum.stealth.explore.server.category.dto.CategoryDtoMapper;
import yandex.practicum.stealth.explore.server.category.dto.NewCategoryDto;
import yandex.practicum.stealth.explore.server.category.model.Category;
import yandex.practicum.stealth.explore.server.event.dao.EventRepository;
import yandex.practicum.stealth.explore.server.exception.ConditionsNotMetException;
import yandex.practicum.stealth.explore.server.exception.CustomEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static yandex.practicum.stealth.explore.server.category.dto.CategoryDtoMapper.catToDto;
import static yandex.practicum.stealth.explore.server.category.dto.CategoryDtoMapper.newDtoToCat;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public Category findCategoryById(Long catId) {
        return categoryRepository.findById(catId).orElseThrow(() -> {
            throw new CustomEntityNotFoundException(String.format("Category with id=%s was not found.", catId));
        });
    }

    // "/categories" endpoints

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        return categoryRepository.findAll().stream()
                .skip(from)
                .limit(size)
                .map(CategoryDtoMapper::catToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        return CategoryDtoMapper.catToDto(findCategoryById(catId));
    }
    // "/admin/categories" endpoints

    @Override
    @Transactional
    public CategoryDto addCategory(NewCategoryDto body) {
        Category category = newDtoToCat(body);
        return catToDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(CategoryDto body) {
        Category category = findCategoryById(body.getId());
        category.setName(body.getName());
        return catToDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long catId) {
        findCategoryById(catId);
        if (eventRepository.findEventsByCategory_Id(catId).stream().findAny().isEmpty()) {
            categoryRepository.deleteById(catId);
        } else {
            throw new ConditionsNotMetException(String.format("Some events linked to category id=%s", catId));
        }
    }
}
