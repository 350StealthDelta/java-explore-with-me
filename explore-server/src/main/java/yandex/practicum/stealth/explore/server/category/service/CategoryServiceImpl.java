package yandex.practicum.stealth.explore.server.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yandex.practicum.stealth.explore.server.category.dao.CategoryRepository;
import yandex.practicum.stealth.explore.server.category.dto.CategoryDto;
import yandex.practicum.stealth.explore.server.category.dto.CategoryDtoMapper;
import yandex.practicum.stealth.explore.server.category.dto.NewCategoryDto;
import yandex.practicum.stealth.explore.server.category.model.Category;
import yandex.practicum.stealth.explore.server.event.dao.EventRepository;
import yandex.practicum.stealth.explore.server.exception.ConditionsNotMetException;
import yandex.practicum.stealth.explore.server.exception.NotFoundException;
import yandex.practicum.stealth.explore.server.util.CustomPageRequest;

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
    public Category findById(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> {
                    throw new NotFoundException(
                            String.format("Category with id=%s was not found.", catId));
                });
    }

    // "/categories" endpoints

    @Override
    public List<CategoryDto> getAll(Integer from, Integer size) {
        PageRequest pageRequest = CustomPageRequest.of(from, size, Sort.unsorted());

        return categoryRepository.findAll(pageRequest).stream()
                .map(CategoryDtoMapper::catToDto)
                .collect(Collectors.toList());
    }

    // "/admin/categories" endpoints

    @Override
    @Transactional
    public CategoryDto create(NewCategoryDto body) {
        Category saved = categoryRepository.save(newDtoToCat(body));

        return catToDto(saved);
    }

    @Override
    @Transactional
    public CategoryDto update(CategoryDto body) {
        Category category = findById(body.getId());
        category.setName(body.getName());

        Category saved = categoryRepository.save(category);
        return catToDto(saved);
    }

    @Override
    @Transactional
    public void deleteById(Long catId) {
        findById(catId);
        if (eventRepository.findEventsByCategory_Id(catId).stream().findAny().isEmpty()) {
            categoryRepository.deleteById(catId);
        } else {
            throw new ConditionsNotMetException(
                    String.format("Some events linked to category id=%s", catId));
        }
    }
}
