package yandex.practicum.stealth.explore.server.comment.dto;

import yandex.practicum.stealth.explore.server.comment.model.Comment;
import yandex.practicum.stealth.explore.server.event.model.Event;
import yandex.practicum.stealth.explore.server.user.model.User;
import yandex.practicum.stealth.explore.server.util.CommentStatus;

import java.time.LocalDateTime;

public class CommentMapperDto {

    public static Comment newDtoToComment(NewCommentDto dto, Event event, User user) {
        return Comment.builder()
                .event(event)
                .user(user)
                .text(dto.getText())
                .status(CommentStatus.PENDING)
                .created(LocalDateTime.now())
                .build();
    }

    public static OutCommentDto commentToDto(Comment comment) {
        return OutCommentDto.builder()
                .id(comment.getId())
                .eventId(comment.getEvent().getId())
                .userId(comment.getUser().getId())
                .text(comment.getText())
                .status(comment.getStatus())
                .created(comment.getCreated())
                .build();
    }
}
