package yandex.practicum.stealth.explore.server.event.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Setter
@Getter
@ToString
@Builder
@Jacksonized
public class EventShortDto extends AbstractEventDto {
}