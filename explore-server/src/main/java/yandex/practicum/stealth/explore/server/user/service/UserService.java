package yandex.practicum.stealth.explore.server.user.service;

import yandex.practicum.stealth.explore.server.user.dto.NewUserRequest;
import yandex.practicum.stealth.explore.server.user.dto.UserDto;
import yandex.practicum.stealth.explore.server.user.model.User;

import java.util.List;

public interface UserService {

    User getUserById(Long userId);

    UserDto addUser(NewUserRequest body);

    void deleteUserById(Long userId);

    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);
}
