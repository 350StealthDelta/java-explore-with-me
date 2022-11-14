package yandex.practicum.stealth.explore.stat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class StatController {

    @PostMapping("/hit")
    public void addRequestToStat(HttpServletRequest request) {
        log.info("=== Call {}", request.getRequestURI());
    }

    @GetMapping("/stats")
    public void getStatistic(@RequestParam String start,
                             @RequestParam String end,
                             @RequestParam(required = false) String[] uris,
                             @RequestParam(defaultValue = "false") Boolean unique,
                             HttpServletRequest request) {
        log.info("=== Call {} with 'start': {}, 'end': {}, 'uris': {}, 'unique': {}",
                request.getRequestURI(), start, end, uris, unique);
    }
}
