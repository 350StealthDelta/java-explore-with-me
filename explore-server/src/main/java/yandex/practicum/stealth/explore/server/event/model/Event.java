package yandex.practicum.stealth.explore.server.event.model;

import lombok.*;
import yandex.practicum.stealth.explore.server.category.model.Category;
import yandex.practicum.stealth.explore.server.user.model.User;
import yandex.practicum.stealth.explore.server.util.EventState;

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
@Table(name = "events", schema = "public")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "events_id")
    private Long id;
    @Column(name = "annotation", nullable = false)
    @Size(min = 20, max = 2000)
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "events_category_id", nullable = false)
    private Category category;
    @Column(name = "confirmed_request")
    private Long confirmedRequests;
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    @Column(name = "description", nullable = false)
    @Size(min = 20, max = 7000)
    private String description;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "initiator", nullable = false)
    private User initiator;
    @ManyToOne
    @JoinColumn(name = "events_location")
    private Location location;
    @Column(name = "paid")
    private Boolean paid;
    @Column(name = "participant_limit")
    private Integer participantLimit;
    @Column(name = "published_on", nullable = false)
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventState state;
    @Column(name = "events_title", nullable = false)
    @Size(min = 3, max = 120)
    private String title;
    @Column(name = "views")
    private Long views;

    public void addView() {
        views++;
    }
}