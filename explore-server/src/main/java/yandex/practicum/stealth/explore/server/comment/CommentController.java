package yandex.practicum.stealth.explore.server.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yandex.practicum.stealth.explore.server.comment.dto.NewCommentDto;
import yandex.practicum.stealth.explore.server.comment.dto.OutCommentDto;
import yandex.practicum.stealth.explore.server.comment.service.CommentService;
import yandex.practicum.stealth.explore.server.util.CommentSort;
import yandex.practicum.stealth.explore.server.util.OnCreate;

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

    /**
     * Добавить комментарий пользователя для выбранного события (добавляется в статусе PENDING).
     *
     * @param body - тело запроса. Обязательные userId, eventId, text.
     * @return - сохраненный в базе комментарий.
     */
    @PostMapping
    public OutCommentDto addComment(@RequestBody @Validated({OnCreate.class}) NewCommentDto body,
                                    HttpServletRequest request) {
        log.info("=== Call 'POST:{}' with 'body:' {}",
                request.getRequestURI(), body);
        return commentService.addComment(body);
    }

    /**
     * Отредактировать размещенный комментарий (редактировать можно сообщения в статусе PENDING или PUBLISHED).
     *
     * @param commentId - обязательный параметр, идентификатор комментария для редактирования.
     * @param body      - тело запроса. Обязательный параметр text.
     * @return - сохраненный в базе обновленный комментарий.
     */
    @PatchMapping("/{commentId}")
    public OutCommentDto editComment(@PathVariable Long commentId,
                                     @RequestBody @Valid NewCommentDto body,
                                     HttpServletRequest request) {
        log.info("=== Call 'PATCH:{}' with 'commentId:' {}, 'body:' {}",
                request.getRequestURI(), commentId, body);
        return commentService.editComment(commentId, body);
    }

    /**
     * Удалить комментарий (удалить можно сообщения в статусе PENDING или PUBLISHED).
     *
     * @param commentId - обязательный параметр, идентификатор комментария для удаления.
     * @param userId    - обязательный параметр, идентификатор пользователя, который создал комментарий.
     */
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId,
                              @RequestParam Long userId,
                              HttpServletRequest request) {
        log.info("=== Call 'DELETE:{}' with 'commentId:' {}, 'userId': {}",
                request.getRequestURI(), commentId, userId);
        commentService.deleteComment(commentId, userId);
    }

    /**
     * Утвердить комментарий модератором-админом (утвердить можно сообщение в статусе PENDING).
     *
     * @param commentId - обязательный параметр, идентификатор комментария для принятия модератором.
     */
    @PatchMapping("/admin/{commentId}/accept")
    public void acceptCommentByAdmin(@PathVariable Long commentId,
                                     HttpServletRequest request) {
        log.info("=== Call 'PATCH:{}' with 'commentId:' {}",
                request.getRequestURI(), commentId);
        commentService.acceptComment(commentId);
    }

    /**
     * Отклонить комментарий модератором-админом (отклонить можно сообщение в статусе PENDING).
     *
     * @param commentId - обязательный параметр, идентификатор комментария для отклонения модератором.
     */
    @PatchMapping("/admin/{commentId}/cancel")
    public void cancelCommentByAdmin(@PathVariable Long commentId,
                                     HttpServletRequest request) {
        log.info("=== Call 'PATCH:{}' with 'commentId:' {}",
                request.getRequestURI(), commentId);
        commentService.cancelComment(commentId);
    }

    /**
     * Получить все комментарии к выбранному событию (public). Сортировка по дате создания.
     *
     * @param eventId - обязательный параметр, идентификатор события, к которому нужно вернуть комментарии.
     * @param sort    - необязательный параметр, направление сортировки сортировки.
     *                Значение по умолчанию "DESC".
     * @param from    - необязательный параметр, количество сообщений, которые  нужно пропустить при пагинации.
     *                Значение по умолчанию "0".
     * @param size    - необязательный параметр, количество сообщений, которые нужно показать на странице пагинации.
     *                Значение по умолчанию "10".
     * @return - возвращает отсортированный список сообщений с учетом пагинации.
     */
    @GetMapping("/{eventId}")
    public List<OutCommentDto> getAllCommentsForEvent(@PathVariable Long eventId,
                                                      @RequestParam(required = false, defaultValue = "DESC")
                                                      CommentSort sort,
                                                      @RequestParam(required = false, defaultValue = "0")
                                                      Integer from,
                                                      @RequestParam(required = false, defaultValue = "10")
                                                      Integer size,
                                                      HttpServletRequest request) {
        log.info("=== Call 'GET:{}' with 'eventId': {}, 'sort': {}, 'from': {}, 'size': {}",
                request.getRequestURI(), eventId, sort, from, size);
        return commentService.getComments(eventId, sort, from, size);
    }
}
