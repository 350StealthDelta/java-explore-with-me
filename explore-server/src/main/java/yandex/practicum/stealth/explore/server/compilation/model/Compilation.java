package yandex.practicum.stealth.explore.server.compilation.model;

import lombok.*;
import yandex.practicum.stealth.explore.server.event.model.Event;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "compilations", schema = "public")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cp_id")
    private Long id;

    @Column(name = "cp_pinned")
    private Boolean pinned;

    @Column(name = "cp_title")
    @Size(min = 3, max = 120)
    private String title;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "comp_events",
            joinColumns = {@JoinColumn(name = "ce_compilation_id")},
            inverseJoinColumns = {@JoinColumn(name = "ce_event_id")})
    @ToString.Exclude
    private List<Event> events = new ArrayList<>();
}
