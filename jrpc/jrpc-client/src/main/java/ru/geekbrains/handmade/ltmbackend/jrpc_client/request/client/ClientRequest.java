package ru.geekbrains.handmade.ltmbackend.jrpc_client.request.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import ru.geekbrains.handmade.ltmbackend.jrpc_client.request.base.AbstractJrpcRequest;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.HandlerName;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.client.ClientDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.user.UserDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClientRequest extends AbstractJrpcRequest {

    @SneakyThrows
    public ClientDto getCurrent() {
        String uri = HandlerName.client.path + "." + HandlerName.client.getCurrent;
        JsonNode response = performJrpcRequest(uri, null);
        return objectMapper.treeToValue(response, ClientDto.class);
    }

    @SneakyThrows
    public Long save(ClientDto client) {
        String uri = HandlerName.client.path + "." + HandlerName.client.save;
        JsonNode response = performJrpcRequest(uri, client);
        return objectMapper.treeToValue(response, Long.class);
    }
}
