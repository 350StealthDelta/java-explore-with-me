package yandex.practicum.stealth.explore.server.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yandex.practicum.stealth.explore.server.user.dto.NewUserRequest;
import yandex.practicum.stealth.explore.server.user.dto.UserDto;
import yandex.practicum.stealth.explore.server.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
@Slf4j
public class AdminUsersController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getAdminUsers(@RequestParam(name = "ids", required = false) List<Long> ids,
                                       @RequestParam(name = "from", defaultValue = "0", required = false) Integer from,
                                       @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
                                       HttpServletRequest request) {
        log.info("=== Call 'GET:{}' with 'ids': {}, 'from': {}, 'size': {}",
                request.getRequestURI(), ids, from, size);

        return userService.getUsers(ids, from, size);
    }

    @PostMapping
    public UserDto addAdminUser(@RequestBody @Valid NewUserRequest body,
                                HttpServletRequest request) {
        log.info("=== Call 'POST:{}' with 'body': {}",
                request.getRequestURI(), body);

        return userService.addUser(body);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteAdminUser(@PathVariable Long userId,
                                                HttpServletRequest request) {
        log.info("=== Call 'DELETE:{}' with 'userId': {}",
                request.getRequestURI(), userId);

        userService.deleteUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
