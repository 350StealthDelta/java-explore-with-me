package yandex.practicum.stealth.explore.server.events;

import org.springframework.stereotype.Service;

@Service
public class EventStatClient /*extends BaseClient*/ {

    private static final String API_PREFIX = "/events";

    /*@Autowired
    public EventStatClient(@Value("${explore-stats.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }*/
}
