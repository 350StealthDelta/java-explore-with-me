package yandex.practicum.stealth.explore.server.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yandex.practicum.stealth.explore.server.compilation.dto.CompilationDto;
import yandex.practicum.stealth.explore.server.compilation.dto.NewCompilationDto;
import yandex.practicum.stealth.explore.server.compilation.service.CompilationService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationsController {

    private final CompilationService compilationService;

    @PostMapping
    public CompilationDto addEventCompilation(@RequestBody @Valid NewCompilationDto body,
                                              HttpServletRequest request) {
        log.info("=== Call 'POST:{}' with 'body': {}",
                request.getRequestURI(), body);
        return compilationService.add(body);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Void> deleteEventCompilation(@PathVariable Long compId,
                                                       HttpServletRequest request) {
        log.info("=== Call 'DELETE:{}' with 'compId': {}",
                request.getRequestURI(), compId);
        compilationService.deleteById(compId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public ResponseEntity<Void> deleteEventFromCompilation(@PathVariable Long compId,
                                                           @PathVariable Long eventId,
                                                           HttpServletRequest request) {
        log.info("=== Call 'DELETE:{}' with 'compId': {}, 'eventId': {}",
                request.getRequestURI(), compId, eventId);
        compilationService.deleteEventFromCompById(compId, eventId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public ResponseEntity<Void> addEventToCompilation(@PathVariable Long compId,
                                                      @PathVariable Long eventId,
                                                      HttpServletRequest request) {
        log.info("=== Call 'PATCH:{}' with 'compId': {}, 'eventId': {}",
                request.getRequestURI(), compId, eventId);
        compilationService.addEventToCompById(compId, eventId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{compId}/pin")
    public ResponseEntity<Void> deleteCompilationPin(@PathVariable Long compId,
                                                     HttpServletRequest request) {
        log.info("=== Call 'DELETE:{}' with 'compId': {}",
                request.getRequestURI(), compId);
        compilationService.switchPin(compId, false);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{compId}/pin")
    public ResponseEntity<Void> addCompilationPin(@PathVariable Long compId,
                                                  HttpServletRequest request) {
        log.info("=== Call 'PATCH:{}' with 'compId': {}",
                request.getRequestURI(), compId);
        compilationService.switchPin(compId, true);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
