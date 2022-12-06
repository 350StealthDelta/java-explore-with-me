package yandex.practicum.stealth.explore.server.user.dto;

import org.springframework.stereotype.Component;
import yandex.practicum.stealth.explore.server.user.model.User;

@Component
public class UserDtoMapper {

    public static UserDto userToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public static UserShortDto userToShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public static User userDtoToUser(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .name(dto.getName())
                .build();
    }

    public static User newDtoToUser(NewUserRequest body) {
        return User.builder()
                .name(body.getName())
                .email(body.getEmail())
                .build();
    }
}
