package yandex.practicum.stealth.explore.server.compilations;

import org.springframework.stereotype.Service;

@Service
public class CompilationStatClient /*extends BaseClient*/ {

    private static final String API_PREFIX = "/compilations";

    /*@Autowired
    public CompilationStatClient(@Value("${explore-stats.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }*/
}
