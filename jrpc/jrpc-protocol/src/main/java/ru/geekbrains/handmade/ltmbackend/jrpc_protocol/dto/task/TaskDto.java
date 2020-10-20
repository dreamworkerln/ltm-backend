package ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task;

import lombok.Data;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.AbstractDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.address.AddressDto;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class TaskDto extends AbstractDto {

    private String title;
    private Long parentId;
    private Set<TaskDto> subtasks = new HashSet<>();
    private Set<TaskMemberDto> members = new HashSet<>();
    private AddressDto address;
    private Instant deadline;
}
