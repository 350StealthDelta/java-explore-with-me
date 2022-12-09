package yandex.practicum.stealth.explore.server.comment.service;

import yandex.practicum.stealth.explore.server.comment.dto.NewCommentDto;
import yandex.practicum.stealth.explore.server.comment.dto.OutCommentDto;
import yandex.practicum.stealth.explore.server.util.CommentSort;

import java.util.List;

public interface CommentService {

    OutCommentDto addComment(NewCommentDto body);

    OutCommentDto editComment(Long commentId, NewCommentDto body);

    void deleteComment(Long commentId, Long userId);

    void acceptComment(Long commentId);

    void cancelComment(Long commentId);

    List<OutCommentDto> getComments(Long eventId, CommentSort sort, Integer from, Integer size);
}
