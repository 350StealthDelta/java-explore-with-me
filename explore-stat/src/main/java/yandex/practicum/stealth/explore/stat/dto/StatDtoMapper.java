package yandex.practicum.stealth.explore.stat.dto;

import org.springframework.stereotype.Component;
import yandex.practicum.stealth.explore.stat.model.StatEntity;
import yandex.practicum.stealth.explore.stat.model.ViewEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StatDtoMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static StatEntity inputDtoToStat(EndpointHit inputDto) {
        return StatEntity.builder()
                .id(inputDto.getId())
                .app(inputDto.getApp())
                .uri(inputDto.getUri())
                .ip(inputDto.getIp())
                .timestamp(LocalDateTime.parse(inputDto.getTimestamp(), formatter))
                .build();
    }

    public static ViewStats viewToOutDto(ViewEntity viewEntity) {
        return ViewStats.builder()
                .app(viewEntity.getApp())
                .uri(viewEntity.getUri())
                .hits(viewEntity.getHits())
                .build();
    }

    public static ViewEntity statToView(StatEntity statEntity) {
        return ViewEntity.builder()
                .app(statEntity.getApp())
                .uri(statEntity.getUri())
                .hits(0L)
                .build();
    }

    public static DateTimeFormatter getFormatter() {
        return formatter;
    }
}
