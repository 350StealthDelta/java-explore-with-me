package yandex.practicum.stealth.explore.server.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yandex.practicum.stealth.explore.server.comment.dao.CommentRepository;
import yandex.practicum.stealth.explore.server.comment.dto.CommentMapperDto;
import yandex.practicum.stealth.explore.server.comment.dto.NewCommentDto;
import yandex.practicum.stealth.explore.server.comment.dto.OutCommentDto;
import yandex.practicum.stealth.explore.server.comment.model.Comment;
import yandex.practicum.stealth.explore.server.event.dao.EventRepository;
import yandex.practicum.stealth.explore.server.event.model.Event;
import yandex.practicum.stealth.explore.server.exception.ConditionsNotMetException;
import yandex.practicum.stealth.explore.server.exception.NotFoundException;
import yandex.practicum.stealth.explore.server.user.dao.UserRepository;
import yandex.practicum.stealth.explore.server.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

import static yandex.practicum.stealth.explore.server.comment.dto.CommentMapperDto.*;
import static yandex.practicum.stealth.explore.server.util.CommentStatus.*;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    @Override
    public OutCommentDto addComment(Long eventId, Long userId, NewCommentDto body) {
        Event event = findEventById(eventId);
        User user = findUserById(userId);
        Comment comment = newDtoToComment(body, event, user);

        return commentToDto(commentRepository.save(comment));
    }

    @Override
    public OutCommentDto editComment(Long commentId, Long userId, NewCommentDto body) {
        Comment comment = findCommentById(commentId);
        if (!comment.getUser().getId().equals(userId)) {
            throw new ConditionsNotMetException(
                    String.format("User with id=%s is not a creator of comment id=%s", userId, commentId));
        }
        updateCommentData(comment, body);
        return commentToDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long commentId) {
        findCommentById(commentId);
        commentRepository.deleteById(commentId);
    }

    @Override
    public void acceptComment(Long commentId) {
        Comment comment = findCommentById(commentId);
        if (!comment.getStatus().equals(PENDING)) {
            throw new ConditionsNotMetException("Only comment with status PENDING can be ACCEPTED");
        }
        comment.setStatus(ACCEPTED);
        commentRepository.save(comment);
    }

    @Override
    public void cancelComment(Long commentId) {
        Comment comment = findCommentById(commentId);
        if (!comment.getStatus().equals(PENDING)) {
            throw new ConditionsNotMetException("Only comment with status PENDING can be CANCELLED");
        }
        comment.setStatus(REJECTED);
        commentRepository.save(comment);
    }

    @Override
    public List<OutCommentDto> getComments(Long eventId) {
        return commentRepository.findAllComments(eventId).stream()
                .map(CommentMapperDto::commentToDto)
                .collect(Collectors.toList());
    }

    private Comment findCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException(String.format("Comment with id=%s was not found.", id));
        });
    }

    private Event findEventById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException(String.format("Event with id=%s was not found.", id));
        });
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException(String.format("User with id=%s was not found.", id));
        });
    }

    private void updateCommentData(Comment comment, NewCommentDto body) {
        comment.setText(
                body.getText() != null ? body.getText() : comment.getText()
        );
    }
}
