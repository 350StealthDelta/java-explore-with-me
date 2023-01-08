package yandex.practicum.stealth.explore.stat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yandex.practicum.stealth.explore.stat.dto.EndpointHit;
import yandex.practicum.stealth.explore.stat.dto.ViewStats;
import yandex.practicum.stealth.explore.stat.service.StatService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatController {

    private final StatService service;

    @PostMapping("/hit")
    public ViewStats addRequestToStat(@RequestBody @Valid EndpointHit inputDto,
                                      HttpServletRequest request) {
        log.info("=== Call 'POST:{}' with 'inputDto': {}", request.getRequestURI(), inputDto);

        return service.addEventStatistic(inputDto);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStatistic(@RequestParam String start,
                                        @RequestParam String end,
                                        @RequestParam(required = false) List<String> uris,
                                        @RequestParam(required = false, defaultValue = "false") Boolean unique,
                                        HttpServletRequest request) {
        log.info("=== Call 'GET:{}' with 'start': {}, 'end': {}, 'uris': {}, 'unique': {}",
                request.getRequestURI(), start, end, uris, unique);
        List<ViewStats> stats = service.getEvents(start, end, uris, unique);
        log.info("=== RESULT: hits count={}", stats.size());

        return stats;
    }

}
