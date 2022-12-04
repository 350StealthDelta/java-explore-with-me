package yandex.practicum.stealth.explore.server.comment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yandex.practicum.stealth.explore.server.comment.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment AS c " +
            "WHERE c.event.id = :id")
    List<Comment> findAllComments(@Param("id") Long eventId);
}
