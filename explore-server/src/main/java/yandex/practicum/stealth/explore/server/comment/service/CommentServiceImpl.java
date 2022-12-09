package yandex.practicum.stealth.explore.server.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import yandex.practicum.stealth.explore.server.util.CommentSort;
import yandex.practicum.stealth.explore.server.util.CustomPageRequest;

import java.util.List;
import java.util.stream.Collectors;

import static yandex.practicum.stealth.explore.server.comment.dto.CommentMapperDto.commentToDto;
import static yandex.practicum.stealth.explore.server.comment.dto.CommentMapperDto.newDtoToComment;
import static yandex.practicum.stealth.explore.server.util.CommentStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    @Override
    public OutCommentDto addComment(NewCommentDto body) {
        Event event = findEventById(body.getEventId());
        User user = findUserById(body.getUserId());
        Comment comment = newDtoToComment(body, event, user);

        return commentToDto(commentRepository.save(comment));
    }

    @Override
    public OutCommentDto editComment(Long commentId, NewCommentDto body) {
        Comment comment = findCommentById(commentId);
        if (comment.getUser().getId().equals(body.getUserId())) {
            updateCommentData(comment, body);

            return commentToDto(commentRepository.save(comment));
        } else {
            throw new ConditionsNotMetException(
                    String.format("User with id=%s is not a creator of comment id=%s", body.getUserId(), commentId));
        }
    }

    @Override
    public void deleteComment(Long commentId, Long userId) {
        findUserById(userId);
        findCommentById(commentId);
        if (findCommentById(userId).getId().equals(findCommentById(commentId).getUser().getId())) {
            commentRepository.deleteById(commentId);
        } else {
            throw new ConditionsNotMetException(
                    String.format("User with id=%s is not a creator of comment id=%s", userId, commentId));
        }
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
    @Transactional(readOnly = true)
    public List<OutCommentDto> getComments(Long eventId, CommentSort sort, Integer from, Integer size) {
        switch (sort) {
            case ASC: {
                PageRequest pageRequest = CustomPageRequest.of(from, size, Sort.by("created").ascending());
                return commentRepository.findAllByEvent_Id(eventId, pageRequest).stream()
                        .map(CommentMapperDto::commentToDto)
                        .collect(Collectors.toList());
            }

            case DESC: {
                PageRequest pageRequest = CustomPageRequest.of(from, size, Sort.by("created").descending());
                return commentRepository.findAllByEvent_Id(eventId, pageRequest).stream()
                        .map(CommentMapperDto::commentToDto)
                        .collect(Collectors.toList());
            }

            default: {
                PageRequest pageRequest = CustomPageRequest.of(from, size);
                return commentRepository.findAllByEvent_Id(eventId, pageRequest).stream()
                        .map(CommentMapperDto::commentToDto)
                        .collect(Collectors.toList());
            }
        }
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
