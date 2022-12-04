package yandex.practicum.stealth.explore.stat.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "stats", schema = "public")
public class StatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "s_id")
    private Long id;

    @Column(name = "s_app")
    private String app;

    @Column(name = "s_uri")
    private String uri;

    @Column(name = "s_ip")
    private String ip;

    @Column(name = "s_timestamp")
    private LocalDateTime timestamp;
}
