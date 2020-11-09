package ru.geekbrains.handmade.ltmbackend.core.converters.task;


import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import ru.geekbrains.handmade.ltmbackend.core.converters._base.AbstractMapper;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.Task;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.TaskMember;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.task.TaskMemberService;
import ru.geekbrains.handmade.ltmbackend.core.services.task.TaskService;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task.TaskDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task.TaskMemberDto;

import javax.annotation.PostConstruct;
import java.util.Map;


@Mapper(config = AbstractMapper.class)
public abstract class TaskMemberMapper extends AbstractMapper<TaskMember, TaskMemberDto> {

    @Autowired
    TaskMemberService taskMemberService;
    @Autowired
    UserService userService;
    @Autowired
    TaskService taskService;

    @PostConstruct
    private void postConstruct() {
        this.baseRepoAccessService = taskMemberService;
    }

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "task.id", target = "taskId")
    public abstract TaskMemberDto toDto(TaskMember taskMember);
    //public abstract TaskMember toEntity(TaskMemberDto tmDto);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "task", ignore = true)
    public abstract TaskMember toEntity(TaskMemberDto tmDto);

//    @AfterMapping
//    Task afterMapping(TaskMemberDto source, @MappingTarget TaskMember target) {
//
//        // get user from DB
//        User user = userService.findByIdOrError(source.getUserId());
//        return merge(source, target);
//    }



    @Override
    @AfterMapping
    public TaskMember afterMapping(TaskMemberDto source, @MappingTarget TaskMember target) {

        // get user from DB
        User user = userService.findByIdOrError(source.getUserId());
        target.setUser(user);

        // source.getTaskId() may == null if this is save of new task
        if (source.getTaskId() != null) {
            Task task = taskService.findByIdOrError(source.getTaskId());
            target.setTask(task);
        }

        return merge(source, target);
    }


}
