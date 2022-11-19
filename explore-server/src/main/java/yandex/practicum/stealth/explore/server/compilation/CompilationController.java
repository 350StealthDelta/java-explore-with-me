package yandex.practicum.stealth.explore.server.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import yandex.practicum.stealth.explore.server.compilation.dto.CompilationDto;
import yandex.practicum.stealth.explore.server.compilation.service.CompilationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompilationController {

    private final CompilationService service;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                @RequestParam(defaultValue = "0") Integer from,
                                                @RequestParam(defaultValue = "10") Integer size,
                                                HttpServletRequest request) {
        log.info("=== Call 'GET:{}', with 'pinned': {}, 'from': {}, 'size': {}",
                request.getRequestURI(), pinned, from, size);
        return service.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable Long compId, HttpServletRequest request) {
        log.info("=== Call 'GET:{}' with 'compId': {}", request.getRequestURI(), compId);
        return service.getCompilationById(compId);
    }
}
