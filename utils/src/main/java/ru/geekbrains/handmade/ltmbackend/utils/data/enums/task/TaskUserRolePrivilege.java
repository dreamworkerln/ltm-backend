package ru.geekbrains.handmade.ltmbackend.utils.data.enums.task;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Назначение привилегий по ролям, хранится только в коде
 */
@Component
@Data
public class TaskUserRolePrivilege {
    private final Map<TaskUserRole, Set<TaskUserPrivilege>> rolePrivileges = new HashMap<>();

    // assign privileges to roles
    public TaskUserRolePrivilege() {

        rolePrivileges.put(TaskUserRole.OWNER,
            new HashSet<>(Arrays.asList(
                TaskUserPrivilege.READ,
                TaskUserPrivilege.WRITE,
                TaskUserPrivilege.CREATE_SUBTASK)));

        rolePrivileges.put(TaskUserRole.EXECUTOR,
            new HashSet<>(Arrays.asList(
                TaskUserPrivilege.READ,
                TaskUserPrivilege.WRITE,
                TaskUserPrivilege.CREATE_SUBTASK)));

        rolePrivileges.put(TaskUserRole.REGULAR,
            new HashSet<>(Arrays.asList(
                TaskUserPrivilege.READ)));
    }
}
