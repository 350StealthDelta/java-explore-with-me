package yandex.practicum.stealth.explore.server.comment.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import yandex.practicum.stealth.explore.server.util.OnCreate;
import yandex.practicum.stealth.explore.server.util.OnUpdate;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Jacksonized
public class NewCommentDto {
    @NotNull(groups = {OnCreate.class})
    private Long eventId;
    @NotNull(groups = {OnCreate.class, OnUpdate.class})
    private Long userId;
    @NotNull(groups = {OnCreate.class})
    private String text;
}
