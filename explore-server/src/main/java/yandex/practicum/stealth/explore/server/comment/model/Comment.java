package yandex.practicum.stealth.explore.server.comment.model;

import lombok.*;
import yandex.practicum.stealth.explore.server.event.model.Event;
import yandex.practicum.stealth.explore.server.user.model.User;
import yandex.practicum.stealth.explore.server.util.CommentStatus;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "comments", schema = "public")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cm_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cm_event_id", nullable = false)
    private Event event;
    @ManyToOne
    @JoinColumn(name = "cm_user_id", nullable = false)
    private User user;
    @Column(name = "cm_text", nullable = false)
    @Size(max = 2500)
    private String text;
    @Column(name = "cm_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CommentStatus status;
    @Column(name = "cm_created", nullable = false)
    private LocalDateTime created;
}
