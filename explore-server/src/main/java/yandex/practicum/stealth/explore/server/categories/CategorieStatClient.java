package yandex.practicum.stealth.explore.server.categories;

import org.springframework.stereotype.Service;

@Service
public class CategorieStatClient /*extends BaseClient*/ {

    private static final String API_PREFIX = "/categories";

    /*@Autowired
    public CategorieStatClient(@Value("${explore-stats.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }*/
}
