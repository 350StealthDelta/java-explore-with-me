package yandex.practicum.stealth.explore.server.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    
    @GetMapping
    public void getCategories(@RequestParam(defaultValue = "0") Integer from,
                              @RequestParam(defaultValue = "10") Integer size) {
        
    }
    
    @GetMapping("/{catId}")
    public void getCategoryById(@PathVariable Long catId) {
    
    }
}
