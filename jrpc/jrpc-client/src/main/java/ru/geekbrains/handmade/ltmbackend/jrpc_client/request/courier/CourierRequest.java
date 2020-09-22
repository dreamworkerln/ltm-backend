package ru.geekbrains.handmade.ltmbackend.jrpc_client.request.courier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import ru.geekbrains.handmade.ltmbackend.jrpc_client.request.base.AbstractJrpcRequest;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.HandlerName;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.client.ClientDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.courier.CourierDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.user.UserDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CourierRequest extends AbstractJrpcRequest {

    @SneakyThrows
    public CourierDto getCurrent() {
        String uri = HandlerName.courier.path + "." + HandlerName.courier.getCurrent;
        JsonNode response = performJrpcRequest(uri, null);
        return objectMapper.treeToValue(response, CourierDto.class);
    }

    @SneakyThrows
    public Long save(CourierDto courier) {
        String uri = HandlerName.courier.path + "." + HandlerName.courier.save;
        JsonNode response = performJrpcRequest(uri, courier);
        return objectMapper.treeToValue(response, Long.class);
    }
}
