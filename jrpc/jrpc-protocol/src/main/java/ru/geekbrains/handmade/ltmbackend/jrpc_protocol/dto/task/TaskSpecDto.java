package ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task;

import lombok.Data;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.AbstractSpecDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.client.ClientDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.courier.CourierDto;

@Data
public class TaskSpecDto extends AbstractSpecDto {

    public enum OrderBy {ASC,DESC}

    // null - выдать сразу все задачи, иначе - по сколько выдавать (pagination)
    private Integer limit;

    // пользователь является владельцем задания
    private Boolean isOwner;

    // пользователь является исполнителем задания
    private Boolean isExecutor;

    // пользователь является обычным участником
    private Boolean isRegular;
}
