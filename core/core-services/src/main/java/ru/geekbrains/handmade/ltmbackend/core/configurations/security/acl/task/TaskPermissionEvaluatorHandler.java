package ru.geekbrains.handmade.ltmbackend.core.configurations.security.acl.task;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import ru.geekbrains.handmade.ltmbackend.core.configurations.security.acl.PermissionEvaluatorHandler;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.task.TaskService;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserPrivilege;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserRolePrivilege;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.Task;


@Component
public class TaskPermissionEvaluatorHandler implements PermissionEvaluatorHandler {

    private final static Class<?> TARGET_CLASS = Task.class;

    private final TaskService taskService;
    private final TaskUserRolePrivilege taskUserRolePrivilege;

    public TaskPermissionEvaluatorHandler(TaskService taskService, TaskUserRolePrivilege taskUserRolePrivilege) {
        this.taskService = taskService;
        this.taskUserRolePrivilege = taskUserRolePrivilege;
    }

    @Override
    public String getKey() {
        return TARGET_CLASS.getName();
    }

    @Override
    public boolean hasPermission(User user, Long targetId, Set<String> privileges) {

        AtomicBoolean result = new AtomicBoolean(false);

        //TaskUserPrivilege privilege = (TaskUserPrivilege)permission;

        // 1. check user role on this task
        taskService.getTaskMemberRole(targetId, user)
            .ifPresent(taskUserRole -> {

                // check if taskUserRole contain requested permissions(privileges)
                for (String p : privileges) {

                    TaskUserPrivilege privilege = TaskUserPrivilege.getByName(p);
                    if (!taskUserRolePrivilege.getRolePrivileges().get(taskUserRole).contains(privilege)) {
                        break;
                    }
                    // All is OK
                    result.set(true);
                }
            });

        return result.get();
    }
}
