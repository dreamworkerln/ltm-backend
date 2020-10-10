package ru.geekbrains.handmade.ltmbackend.core.converters.task;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.geekbrains.handmade.ltmbackend.core.converters._base.AbstractMapper;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.Task;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.TaskMember;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.task.TaskMemberService;
import ru.geekbrains.handmade.ltmbackend.core.services.task.TaskService;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task.TaskMemberDto;


@Mapper(config = AbstractMapper.class)
public abstract class TaskMemberMapper extends AbstractMapper<TaskMember, TaskMemberDto> {

    @Autowired
    private TaskService taskService;
    @Autowired
    TaskMemberService taskMemberService;
    @Autowired
    UserService userService;

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "task.id", target = "taskId")
    public abstract TaskMemberDto toDto(TaskMember taskMember);

    public TaskMember toEntity(TaskMemberDto tmDto) {

        User user = userService.findByIdOrError(tmDto.getUserId());
        Task task = taskService.findByIdOrError(tmDto.getTaskId());
        return new TaskMember(task, user, tmDto.getTaskUserRole());
    }
}
