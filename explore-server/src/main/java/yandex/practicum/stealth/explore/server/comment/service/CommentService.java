package yandex.practicum.stealth.explore.server.comment.service;

import yandex.practicum.stealth.explore.server.comment.dto.NewCommentDto;
import yandex.practicum.stealth.explore.server.comment.dto.OutCommentDto;

import java.util.List;

public interface CommentService {

    OutCommentDto addComment(Long eventId, Long userId, NewCommentDto body);

    OutCommentDto editComment(Long commentId, Long userId, NewCommentDto body);

    void deleteComment(Long commentId);

    void acceptComment(Long commentId);

    void cancelComment(Long commentId);

    List<OutCommentDto> getComments(Long eventId);
}
