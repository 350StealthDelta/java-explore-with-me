package yandex.practicum.stealth.explore.server.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yandex.practicum.stealth.explore.server.comment.dto.NewCommentDto;
import yandex.practicum.stealth.explore.server.comment.dto.OutCommentDto;
import yandex.practicum.stealth.explore.server.comment.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/comment")
@RequiredArgsConstructor
@Validated
@Slf4j
public class CommentController {

    private final CommentService commentService;

    // Добавить комментарий пользователя для выбранного события (добавляется в статусе PENDING)
    @PostMapping
    public OutCommentDto addCommentToEvent(@RequestParam(name = "eventId") Long eventId,
                                           @RequestParam(name = "userId") Long userId,
                                           @RequestBody @Valid NewCommentDto body,
                                           HttpServletRequest request) {
        log.info("=== Call 'POST:{}' with 'eventId:' {}, 'userId:' {}, 'body:' {}",
                request.getRequestURI(), eventId, userId, body);
        return commentService.addComment(eventId, userId, body);
    }

    // Отредактировать размещенный комментарий (редактировать можно сообщения в статусе PENDING или PUBLISHED)
    @PatchMapping("/{commentId}")
    public OutCommentDto editComment(@PathVariable Long commentId,
                                     @RequestParam(name = "userId") Long userId,
                                     @RequestBody @Valid NewCommentDto body,
                                     HttpServletRequest request) {
        log.info("=== Call 'PATCH:{}' with 'commentId:' {}, 'userId:' {}, 'body:' {}",
                request.getRequestURI(), commentId, userId, body);
        return commentService.editComment(commentId, userId, body);
    }

    // Удалить комментарий (удалить можно сообщения в статусе PENDING или PUBLISHED)
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId,
                              HttpServletRequest request) {
        log.info("=== Call 'DELETE:{}' with 'commentId:' {}",
                request.getRequestURI(), commentId);
        commentService.deleteComment(commentId);
    }

    // Утвердить комментарий модератором-админом (утвердить можно сообщение в статусе PENDING)
    @PatchMapping("/admin/{commentId}/accept")
    public void acceptCommentByAdmin(@PathVariable Long commentId,
                                     HttpServletRequest request) {
        log.info("=== Call 'PATCH:{}' with 'commentId:' {}",
                request.getRequestURI(), commentId);
        commentService.acceptComment(commentId);
    }

    // Отклонить комментарий модератором-админом (отклонить можно сообщение в статусе PENDING)
    @PatchMapping("/admin/{commentId}/cancel")
    public void cancelCommentByAdmin(@PathVariable Long commentId,
                                     HttpServletRequest request) {
        log.info("=== Call 'PATCH:{}' with 'commentId:' {}",
                request.getRequestURI(), commentId);
        commentService.acceptComment(commentId);
    }

    // Получить все комментарии к выбранному событию (public)
    @GetMapping("/{eventId}")
    public List<OutCommentDto> getAllCommentsForEvent(@PathVariable Long eventId,
                                                      HttpServletRequest request) {
        log.info("=== Call 'GET:{}' with 'eventId:' {}",
                request.getRequestURI(), eventId);
        return commentService.getComments(eventId);
    }
}
