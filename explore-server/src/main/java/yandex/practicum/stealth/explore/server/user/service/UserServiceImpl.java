package yandex.practicum.stealth.explore.server.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yandex.practicum.stealth.explore.server.exception.NotFoundException;
import yandex.practicum.stealth.explore.server.user.dao.UserRepository;
import yandex.practicum.stealth.explore.server.user.dto.NewUserRequest;
import yandex.practicum.stealth.explore.server.user.dto.UserDto;
import yandex.practicum.stealth.explore.server.user.dto.UserDtoMapper;
import yandex.practicum.stealth.explore.server.user.model.User;
import yandex.practicum.stealth.explore.server.util.CustomPageRequest;

import java.util.List;
import java.util.stream.Collectors;

import static yandex.practicum.stealth.explore.server.user.dto.UserDtoMapper.newDtoToUser;
import static yandex.practicum.stealth.explore.server.user.dto.UserDtoMapper.userToDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException(
                    String.format("User with id=%s was not found.", userId));
        });
    }

    // "/admin/users" endpoints
    @Override
    @Transactional
    public UserDto addUser(NewUserRequest body) {

        User user = userRepository.save(newDtoToUser(body));
        return userToDto(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        getUserById(userId);

        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        PageRequest pageRequest = CustomPageRequest.of(from, size, Sort.unsorted());
        List<UserDto> users;

        if (ids != null && ids.size() > 0) {
            users = userRepository.findAll(pageRequest).stream()
                    .filter(user -> ids.contains(user.getId()))
                    .map(UserDtoMapper::userToDto)
                    .collect(Collectors.toList());
        } else {
            users = userRepository.findAll(pageRequest).stream()
                    .skip(from)
                    .limit(size)
                    .map(UserDtoMapper::userToDto)
                    .collect(Collectors.toList());
        }
        return users;
    }
}
