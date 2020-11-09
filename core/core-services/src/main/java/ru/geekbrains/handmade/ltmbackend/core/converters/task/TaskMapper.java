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
import ru.geekbrains.handmade.ltmbackend.core.services.task.TaskService;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task.TaskDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task.TaskMemberDto;


import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = AbstractMapper.class,
    uses = {TaskMemberMapper.class})
public abstract class TaskMapper extends AbstractMapper<Task, TaskDto> {

    @Autowired
    private TaskService taskService;

    @Autowired
    UserService userService;
    @Autowired
    TaskMemberMapper taskMemberMapper;

    @PostConstruct
    private void postConstruct() {
        this.baseRepoAccessService = taskService;
        //constructor = new EntityConstructor();
    }

    //@Mapping(target = "password", ignore = true)
    @Mapping(target = "parentId", source = "parent.id")
    public abstract TaskDto toDto(Task task);

    //@Mapping(target = "refreshTokenList", expression = "java(null)") // всегда подгружаем из БД
    //@Mapping(target = "account", ignore = true)
    //@Mapping(target = "membersPersist", ignore = true)
    @Mapping(target = "parent", ignore = true)
    public abstract Task toEntity(TaskDto taskDto);


    protected Map<User, TaskMember> membersToMap(Set<TaskMemberDto> memberSetDto) {

        Map<User, TaskMember> result = new HashMap<>();

        for (TaskMemberDto tmDto : memberSetDto) {
            TaskMember tm = taskMemberMapper.toEntity(tmDto);
            result.put(tm.getUser(), tm);
        }
        return result;
    }

    protected Set<TaskMemberDto> membersToSet(Map<User, TaskMember> memberMap) {
        return memberMap.values().stream().map(tm -> taskMemberMapper.toDto(tm)).collect(Collectors.toSet());
    }

    @Override
    @AfterMapping
    public Task afterMapping(TaskDto source, @MappingTarget Task target) {

        // load Task parent
        target.setParent(taskService.findById(source.getParentId()).orElse(null));

        // if target.getMembers().task == null then set it to current task
        for (Map.Entry<User, TaskMember> entry : target.getMembers().entrySet()) {
            TaskMember tm = entry.getValue();
            if(tm.getTask() == null) {
                tm.setTask(target);
            }
        }

        return merge(source, target);
    }




//    protected Set<Long> subtaskToDto(Set<Task> subtasks) {
//        return subtasks.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
//    }
//
//    protected Set<Long> subtaskToEntity(Set<Long> subtasks) {
//        return subtasks.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
//    }









//    @AfterMapping
//    UserDto afterMapping(User source, @MappingTarget UserDto target) {
//
//        UserDto result = target;
//        result.getAccount().setUser(result);
//        return result;
//    }




//    protected class EntityConstructor extends Constructor<Task, TaskDto> {
//
//        //private UserRoleService userRoleService;
//
//        @Override
//        public Task create(TaskDto dto, Task entity) {
//
//            // Mapstruct 1.4 maybe will support constructors with params
//            return new Task();
//        }
//
//    }
}
