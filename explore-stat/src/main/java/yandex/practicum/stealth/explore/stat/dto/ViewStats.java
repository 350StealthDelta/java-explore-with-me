package yandex.practicum.stealth.explore.stat.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;
}
