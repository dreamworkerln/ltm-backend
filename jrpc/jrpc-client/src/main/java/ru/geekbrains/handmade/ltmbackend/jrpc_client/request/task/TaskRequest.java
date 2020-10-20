package ru.geekbrains.handmade.ltmbackend.jrpc_client.request.task;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;
import ru.geekbrains.handmade.ltmbackend.jrpc_client.request.base.AbstractJrpcRequest;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.HandlerName;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task.TaskDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task.TaskSpecDto;

import java.util.Arrays;
import java.util.List;

public class TaskRequest extends AbstractJrpcRequest {

    @SneakyThrows
    public List<TaskDto> findAll(TaskSpecDto spec) {
        String uri = HandlerName.task.path + "." + HandlerName.task.findAll;
        JsonNode response = performJrpcRequest(uri, spec);
        return Arrays.asList(objectMapper.treeToValue(response, TaskDto[].class));
    }

    @SneakyThrows
    public TaskDto findById(Long id) {
        String uri = HandlerName.task.path + "." + HandlerName.task.findById;
        JsonNode response = performJrpcRequest(uri, id);
        return objectMapper.treeToValue(response, TaskDto.class);
    }


}
