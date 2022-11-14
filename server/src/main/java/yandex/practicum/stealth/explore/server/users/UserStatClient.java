package yandex.practicum.stealth.explore.server.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import yandex.practicum.stealth.explore.server.client.BaseClient;

@Service
public class UserStatClient /*extends BaseClient*/ {
    
    private static final String API_PREFIX = "/users";
    
    /*@Autowired
    public UserStatClient(@Value("${explore-stats.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }*/
}
