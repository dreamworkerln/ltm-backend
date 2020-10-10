package ru.geekbrains.handmade.ltmbackend.core.converters.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.handmade.ltmbackend.core.converters._base.AbstractConverter;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.Task;
import ru.geekbrains.handmade.ltmbackend.core.specifications.task.TaskSpecBuilder;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task.TaskDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task.TaskSpecDto;


@Component
public class TaskConverter extends AbstractConverter<Task, TaskDto, TaskSpecDto>  {

    @Autowired
    public TaskConverter(TaskMapper userMapper, TaskSpecBuilder taskSpecBuilder) {
        this.entityMapper = userMapper;
        this.specBuilder = taskSpecBuilder;

        this.entityClass = Task.class;
        this.dtoClass = TaskDto.class;
        this.specClass = TaskSpecDto.class;
    }


    @Override
    protected void validate(Task task) {
        super.validate(task);



        // ... custom validation
    }
}
