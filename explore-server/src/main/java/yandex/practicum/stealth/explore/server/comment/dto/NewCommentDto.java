package yandex.practicum.stealth.explore.server.comment.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import yandex.practicum.stealth.explore.server.util.OnCreate;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Jacksonized
public class NewCommentDto {
    //    private Long id;
    @NotNull(groups = {OnCreate.class})
    private Long eventId;
    @NotNull(groups = {OnCreate.class})
    private Long userId;
    @NotNull(groups = {OnCreate.class})
    private String text;
//    private CommentStatus status;
}
