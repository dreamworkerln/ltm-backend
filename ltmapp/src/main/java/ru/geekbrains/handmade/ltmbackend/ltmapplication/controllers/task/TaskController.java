package ru.geekbrains.handmade.ltmbackend.ltmapplication.controllers.task;


import org.springframework.security.access.annotation.Secured;
import ru.geekbrains.handmade.ltmbackend.core.controllers.jrpc.annotations.JrpcController;
import ru.geekbrains.handmade.ltmbackend.core.controllers.jrpc.annotations.JrpcMethod;
import ru.geekbrains.handmade.ltmbackend.core.converters.task.TaskConverter;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.Task;
import ru.geekbrains.handmade.ltmbackend.core.services.task.TaskService;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.HandlerName;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task.TaskDto;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.UserRole;

import java.util.List;

/**
 * Task management
 */
@JrpcController(HandlerName.task.path)
@Secured(UserRole.VAL.USER)
public class TaskController {

    private final TaskService taskService;
    private final TaskConverter converter;

    public TaskController(TaskService taskService, TaskConverter converter) {
        this.taskService = taskService;
        this.converter = converter;
    }

    /**
     * Return tasks for current user
     * @return List<TaskDto>
     */
    @JrpcMethod(HandlerName.task.findByCurrentUser)
    public List<TaskDto> getCurrent() {

        List<Task> tasks = taskService.findByCurrentUser();
        return converter.toDtoList(tasks);
    }


}
