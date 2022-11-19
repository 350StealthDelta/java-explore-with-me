package yandex.practicum.stealth.explore.server.user.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
@Jacksonized
public class NewUserRequest {
    @NotBlank
    private String name;
    @Email
    private String email;
}
