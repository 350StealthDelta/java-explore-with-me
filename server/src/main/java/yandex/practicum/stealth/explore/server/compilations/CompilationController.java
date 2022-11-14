package yandex.practicum.stealth.explore.server.compilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompilationController {
    
    @GetMapping
    public void getCompilations(@RequestParam boolean pinned,
                                @RequestParam Integer from,
                                @RequestParam Integer size) {
    
    }
    
    @GetMapping("/{compId}")
    public void getCompilationById(@PathVariable Long compId) {
    
    }
}
