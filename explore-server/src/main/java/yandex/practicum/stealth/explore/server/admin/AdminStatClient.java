package yandex.practicum.stealth.explore.server.admin;

import org.springframework.stereotype.Service;

@Service
public class AdminStatClient/* extends BaseClient*/ {

    private static final String API_PREFIX = "/categories";

    /*@Autowired
    public AdminStatClient(@Value("${explore-stats.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }*/
}
