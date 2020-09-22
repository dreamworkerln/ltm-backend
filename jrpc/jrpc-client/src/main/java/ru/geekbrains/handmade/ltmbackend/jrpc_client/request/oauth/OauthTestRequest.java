package ru.geekbrains.handmade.ltmbackend.jrpc_client.request.oauth;

import ru.geekbrains.handmade.ltmbackend.jrpc_client.request.base.AbstractRequestWithOauth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OauthTestRequest extends AbstractRequestWithOauth {

    public ResponseEntity<String> test() {
        String url = String.format("http://%1$s:%2$s/oauzz/token/test",
            this.clientProp.getAuthServer().getHostName(),
            this.clientProp.getAuthServer().getPort());

        return performRequest(url, null, String.class);
    }
}
