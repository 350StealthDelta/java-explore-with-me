package yandex.practicum.stealth.explore.server.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import yandex.practicum.stealth.explore.server.category.dto.CategoryDto;
import yandex.practicum.stealth.explore.server.category.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") Integer from,
                                           @RequestParam(defaultValue = "10") Integer size,
                                           HttpServletRequest request) {
        log.info("=== Call 'GET:{}' with 'from': {}, 'size': {}", request.getRequestURI(), from, size);

        return service.getAll(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable Long catId, HttpServletRequest request) {
        log.info("=== Call 'GET:{}' with 'catId': {}", request.getRequestURI(), catId);

        return service.getById(catId);
    }
}
