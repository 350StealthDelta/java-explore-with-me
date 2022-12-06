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
    @Column(name = "e_id")
    private Long id;

    @Column(name = "e_annotation", nullable = false)
    @Size(min = 20, max = 2000)
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "e_cat_id", nullable = false)
    private Category category;

    @Column(name = "e_confirmed_request")
    private Long confirmedRequests;

    @Column(name = "e_created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "e_description", nullable = false)
    @Size(min = 20, max = 7000)
    private String description;

    @Column(name = "e_date")
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "e_initiator", nullable = false)
    private User initiator;

    @ManyToOne
    @JoinColumn(name = "e_location")
    private Location location;

    @Column(name = "e_paid")
    private Boolean paid;

    @Column(name = "e_participant_limit")
    private Integer participantLimit;

    @Column(name = "e_published_on", nullable = false)
    private LocalDateTime publishedOn;

    @Column(name = "e_request_moderation")
    private Boolean requestModeration;

    @Column(name = "e_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventState state;

    @Column(name = "e_title", nullable = false)
    @Size(min = 3, max = 120)
    private String title;

    @Column(name = "e_views")
    private Long views;

    public void addView() {
        views++;
    }
}