package ru.geekbrains.handmade.ltmbackend.ltmapplication.controllers.task;


import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.geekbrains.handmade.ltmbackend.core.controllers.jrpc.annotations.JrpcController;
import ru.geekbrains.handmade.ltmbackend.core.controllers.jrpc.annotations.JrpcMethod;
import ru.geekbrains.handmade.ltmbackend.core.converters.task.TaskConverter;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.Task;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.task.TaskService;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.HandlerName;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task.TaskDto;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.UserRole;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserPrivilege;

import java.util.List;

/**
 * Task management
 */
@JrpcController(HandlerName.task.path)
@Secured(UserRole.VAL.USER)
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final TaskConverter converter;

    public TaskController(TaskService taskService, UserService userService, TaskConverter converter) {
        this.taskService = taskService;
        this.userService = userService;
        this.converter = converter;
    }

    /**
     * Return all tasks for current user
     * @return List<TaskDto>
     */
    @JrpcMethod(HandlerName.task.findAll)
    public List<TaskDto> findAll() {

        User user = userService.getCurrent();
        List<Task> tasks = taskService.findByUser(user);
        tasks.forEach(taskService::truncateLazy);
        return converter.toDtoList(tasks);
    }


    /**
     * Return tasks by id, check permissions
     * @return List<TaskDto>
     */                                      //TaskUserPrivilege.VAL.READ
    @JrpcMethod(HandlerName.task.findById)
    @PreAuthorize("hasPermission(#id, " +
        "T(ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.HandlerName$task).path, " +
        "T(ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserPrivilege).READ)")
    public TaskDto findById(Long id) {


        Task result = taskService.findById(id).orElse(null);
        taskService.truncateLazy(result);
        return converter.toDto(result);
    }


    
    @JrpcMethod(HandlerName.task.save)
    public Long save(TaskDto taskDto) {
        Task task = converter.toEntity(taskDto);
        task = taskService.save(task);
        return task.getId();
    }


//    @JrpcMethod(HandlerName.task.findExperimental)
//    @PreAuthorize("hasPermission(#id, 'update')")
//    public List<TaskDto> findExperimental(Long id) {
//
//        List<Task> tasks = taskService
//        return converter.toDtoList(tasks);
//    }






}
