package yandex.practicum.stealth.explore.stat.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;

//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Stat {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "app")
    private String app;
    @Column(name = "uri")
    private String uri;
    @Column(name = "ip")
    private String ip;
    @Column(name = "timestamp")
    private String timestamp;
}
