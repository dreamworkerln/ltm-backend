package ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task;

import lombok.Data;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.AbstractSpecDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.client.ClientDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.courier.CourierDto;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserRole;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
public class TaskSpecDto extends AbstractSpecDto {

    public enum OrderBy {ASC,DESC}

    // null - выдать сразу все задачи, иначе - по сколько выдавать (pagination)
    private Integer limit;

    // по-умолчанию выдавать все задания, куда входит текущий пользователь, с любой TaskUserRole
    private Set<TaskUserRole> taskRoleFilter = new HashSet<>(Arrays.asList(TaskUserRole.values()));

    //    // пользователь является владельцем задания
//    private Boolean isOwner;
//
//    // пользователь является исполнителем задания
//    private Boolean isExecutor;
//
//    // пользователь является обычным участником
//    private Boolean isRegular;
}
