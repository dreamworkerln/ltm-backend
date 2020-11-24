package ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task;

import lombok.Data;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.AbstractDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.user.UserDto;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserRole;

import java.util.Objects;

@Data
public class TaskMemberDto extends AbstractDto {
        Long userId;
        Long taskId;
        TaskUserRole taskUserRole; // User role in current task

    public TaskMemberDto() {}

    public TaskMemberDto(TaskDto task, UserDto user, TaskUserRole taskUserRole) {
        this.userId = user.getId();
        this.taskId = task.getId();
        this.taskUserRole = taskUserRole;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof TaskMemberDto)) return false;
//        if (!super.equals(o)) return false;
//        TaskMemberDto that = (TaskMemberDto) o;
//        return userId.equals(that.userId) &&
//            taskUserRole == that.taskUserRole;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(userId, taskUserRole);
//    }
}
