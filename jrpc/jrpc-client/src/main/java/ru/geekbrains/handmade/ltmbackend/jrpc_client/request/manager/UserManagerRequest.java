package ru.geekbrains.handmade.ltmbackend.jrpc_client.request.manager;

import com.fasterxml.jackson.databind.JsonNode;
import ru.geekbrains.handmade.ltmbackend.jrpc_client.request.base.AbstractJrpcRequest;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.HandlerName;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.user.UserDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserManagerRequest extends AbstractJrpcRequest {

    @SneakyThrows
    public UserDto findByUsername(String username) {
        String uri = HandlerName.management.user.path + "." + HandlerName.management.user.findByUsername;
        JsonNode response = performJrpcRequest(uri, username);
        return objectMapper.treeToValue(response, UserDto.class);
    }

    @SneakyThrows
    public UserDto findById(long id) {
        String uri = HandlerName.management.user.path + "." + HandlerName.management.user.findById;
        JsonNode response = performJrpcRequest(uri, id);
        return objectMapper.treeToValue(response, UserDto.class);
    }

    @SneakyThrows
    public Long save(UserDto user) {
        String uri = HandlerName.management.user.path + "." + HandlerName.management.user.save;
        JsonNode response = performJrpcRequest(uri, user);
        return objectMapper.treeToValue(response, Long.class);
    }
}
