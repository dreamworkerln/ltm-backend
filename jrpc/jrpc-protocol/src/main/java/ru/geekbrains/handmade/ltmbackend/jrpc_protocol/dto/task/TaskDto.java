package ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.AbstractDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.address.AddressDto;
import ru.geekbrains.handmade.ltmbackend.utils.SetUtils;

import java.time.Instant;
import java.util.*;

@Data
@Slf4j
public class TaskDto extends AbstractDto {

    private String title;
    private Long parentId;
    private Set<TaskDto> subtasks = new HashSet<>();
    private Set<TaskMemberDto> members = new HashSet<>();
    private AddressDto address;
    private Instant deadline;

    @Override
    public String toString() {
        return "TaskDto{" +
            "title='" + title + '\'' +
            ", id=" + id +
            ", parentId=" + parentId +
            '}';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof TaskDto)) return false;
//        if (!super.equals(o)) return false;
//        TaskDto taskDto = (TaskDto) o;
//
//        return title.equals(taskDto.title) &&
//            SetUtils.equals(subtasks, taskDto.subtasks) &&
//            SetUtils.equals(members, taskDto.members) &&
//            Objects.equals(address, taskDto.address) &&
//            Objects.equals(deadline, taskDto.deadline);
//    }

//    @Override
//    public int hashCode() {
//        int result = Objects.hash(title, subtasks, members, address, deadline);
//        log.info("---------------");
//        log.info("TaskDto {} ", this);
//        log.info("title: {}", Objects.hash(title));
//        log.info("subtasks: {}", Objects.hash(subtasks));
//        log.info("members: {}", Objects.hash(members));
//        log.info("deadline: {}", Objects.hash(deadline));
//        log.info("TaskDto {}, hash: {}", this, result);
//        log.info("---------------");
//        return result;
//    }
}
