package ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task;

import lombok.Data;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.AbstractDto;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserRole;

@Data
public class TaskMemberDto extends AbstractDto {
        Long userId;
        Long taskId;
        TaskUserRole taskUserRole; // User role in current task
    }
