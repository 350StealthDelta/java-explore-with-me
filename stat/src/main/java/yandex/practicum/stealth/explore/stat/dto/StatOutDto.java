package yandex.practicum.stealth.explore.stat.dto;

import lombok.*;

//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class StatOutDto {
    private String app;
    private String uri;
    private Long hits;
}
