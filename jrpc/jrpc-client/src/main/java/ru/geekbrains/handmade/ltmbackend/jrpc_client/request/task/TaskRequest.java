package ru.geekbrains.handmade.ltmbackend.jrpc_client.request.task;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.geekbrains.handmade.ltmbackend.jrpc_client.request.base.AbstractJrpcRequest;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.HandlerName;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task.TaskDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task.TaskSpecDto;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
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

    @SneakyThrows
    public TaskDto fetchAllById(Long id) {
        String uri = HandlerName.task.path + "." + HandlerName.task.fetchAllById;
        JsonNode response = performJrpcRequest(uri, id);
        return objectMapper.treeToValue(response, TaskDto.class);
    }




    @SneakyThrows
    public Long save(TaskDto task) {
        String uri = HandlerName.task.path + "." + HandlerName.task.save;
        JsonNode response = performJrpcRequest(uri, task);
        return objectMapper.treeToValue(response, Long.class);
    }


}
