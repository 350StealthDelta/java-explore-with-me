package yandex.practicum.stealth.explore.server.request.model;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import yandex.practicum.stealth.explore.server.event.model.Event;
import yandex.practicum.stealth.explore.server.user.model.User;
import yandex.practicum.stealth.explore.server.util.ParticipationStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "requests", schema = "public")
@Jacksonized
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rq_requester", nullable = false)
    private User requester;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rq_event", nullable = false)
    private Event event;
    @Column(name = "rq_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParticipationStatus status;
    @Column(name = "rq_created", nullable = false)
    private LocalDateTime created;
}
