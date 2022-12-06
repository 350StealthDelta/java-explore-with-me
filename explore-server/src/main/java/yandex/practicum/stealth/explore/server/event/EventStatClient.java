package yandex.practicum.stealth.explore.server.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import yandex.practicum.stealth.explore.server.client.BaseClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
public class EventStatClient extends BaseClient implements EventStat {

    private static final String API_PREFIX = "";

    @Autowired
    public EventStatClient(@Value("${explore-stats.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    @Override
    public ResponseEntity<Object> addHitToStatistic(HttpServletRequest request) {
        Hit hit = Hit.builder()
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .app("ewm-main-service")
                .timestamp(LocalDateTime.now())
                .build();
        return post("/hit", hit);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    private static class Hit {
        private String app;
        private String uri;
        private String ip;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime timestamp;
    }
}
