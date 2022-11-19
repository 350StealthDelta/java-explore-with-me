package yandex.practicum.stealth.explore.server.user.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
@Jacksonized
public class UserDto {
    private Long id;
    private String email;
    private String name;
}
