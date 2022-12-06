package yandex.practicum.stealth.explore.stat.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ViewEntity {
    private String app;
    private String uri;
    private Long hits;
}
