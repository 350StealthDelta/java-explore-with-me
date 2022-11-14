package yandex.practicum.stealth.explore.server.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
@Slf4j
public class EventController {
    
    @GetMapping
    public void getAllEvents(@RequestParam String text,
                             @RequestParam List<Integer> categories,
                             @RequestParam boolean paid,
                             @RequestParam String rangeStart,
                             @RequestParam String rangeEnd,
                             @RequestParam(defaultValue = "false") boolean onlyAvailable,
                             @RequestParam String sort,
                             @RequestParam(defaultValue = "0") Integer from,
                             @RequestParam(defaultValue = "10") Integer size) {
        log.info("--- Request \"getAllEvents\" with text: {}, categories: {}, paid: {}, rangeStart: {}," +
                " rangeEnd: {}, onlyAvailable: {}, sort: {}, from: {}, size: {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }
    
    @GetMapping("/{id}")
    public void getEventById(@PathVariable Long id) {
        log.info("--- Request \"getEventById\" with id: {}", id);
    }
}
