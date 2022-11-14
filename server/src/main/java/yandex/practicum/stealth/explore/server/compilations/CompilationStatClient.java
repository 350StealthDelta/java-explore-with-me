package yandex.practicum.stealth.explore.server.compilations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import yandex.practicum.stealth.explore.server.client.BaseClient;

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
