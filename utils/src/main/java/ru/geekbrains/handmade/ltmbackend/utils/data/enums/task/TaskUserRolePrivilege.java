package ru.geekbrains.handmade.ltmbackend.utils.data.enums.task;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Привилегии для ролей по Task<br>
 * хранится только в коде
 */
@Component
@Data
public class TaskUserRolePrivilege {
    private final Map<TaskUserRole, Set<TaskUserPrivilege>> rolePrivileges = new HashMap<>();

    // assign privileges to roles
    public TaskUserRolePrivilege() {

        // Владелец задачи может все
        rolePrivileges.put(TaskUserRole.OWNER,
            new HashSet<>(Arrays.asList(
                TaskUserPrivilege.READ,
                TaskUserPrivilege.WRITE,
                TaskUserPrivilege.CREATE_SUBTASK)));

        // Ответственный за исполнение, ну пока тоже может все)
        rolePrivileges.put(TaskUserRole.EXECUTOR,
            new HashSet<>(Arrays.asList(
                TaskUserPrivilege.READ,
                TaskUserPrivilege.WRITE,
                TaskUserPrivilege.CREATE_SUBTASK)));

        // обычный участник может читать данные
        rolePrivileges.put(TaskUserRole.REGULAR,
            new HashSet<>(Collections.singletonList(
                TaskUserPrivilege.READ)));
    }
}
