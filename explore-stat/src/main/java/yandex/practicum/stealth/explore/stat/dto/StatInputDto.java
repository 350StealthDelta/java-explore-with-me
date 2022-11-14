package yandex.practicum.stealth.explore.stat.dto;

import lombok.*;

//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class StatInputDto {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
