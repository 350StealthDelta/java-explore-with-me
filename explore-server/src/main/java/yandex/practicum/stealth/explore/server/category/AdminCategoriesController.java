package yandex.practicum.stealth.explore.server.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yandex.practicum.stealth.explore.server.category.dto.CategoryDto;
import yandex.practicum.stealth.explore.server.category.dto.NewCategoryDto;
import yandex.practicum.stealth.explore.server.category.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminCategoriesController {

    private final CategoryService categoryService;

    @PatchMapping
    public CategoryDto updateAdminCategory(@RequestBody @Valid CategoryDto body,
                                           HttpServletRequest request) {
        log.info("=== Call 'PATCH:{}' with 'body': {}",
                request.getRequestURI(), body);

        return categoryService.update(body);
    }

    @PostMapping
    public CategoryDto addAdminCategory(@RequestBody @Valid NewCategoryDto body,
                                        HttpServletRequest request) {
        log.info("=== Call 'POST:{}' with 'body':{}",
                request.getRequestURI(), body);

        return categoryService.create(body);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Void> deleteAdminCategory(@PathVariable Long catId,
                                                    HttpServletRequest request) {
        log.info("=== Call 'DELETE:{}' with 'catId': {}",
                request.getRequestURI(), catId);
        categoryService.deleteById(catId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
