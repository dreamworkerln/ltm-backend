package ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task;

import lombok.Data;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.user.UserDto;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.TaskUserRole;

@Data
public class TaskMemberDto {

        protected Long id;
        UserDto user;
        TaskDto task;

        // User role in current task
        TaskUserRole taskUserRole;


    }
