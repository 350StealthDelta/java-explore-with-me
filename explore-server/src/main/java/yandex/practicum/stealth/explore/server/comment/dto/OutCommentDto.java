package yandex.practicum.stealth.explore.server.comment.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import yandex.practicum.stealth.explore.server.util.CommentStatus;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Jacksonized
public class OutCommentDto {
    private Long id;
    private Long eventId;
    private Long userId;
    private String text;
    private CommentStatus status;
    private LocalDateTime created;
}
