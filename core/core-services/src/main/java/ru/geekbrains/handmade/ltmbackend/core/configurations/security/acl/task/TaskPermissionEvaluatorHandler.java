package ru.geekbrains.handmade.ltmbackend.core.configurations.security.acl.task;

import org.springframework.stereotype.Component;
import ru.geekbrains.handmade.ltmbackend.core.configurations.security.acl.PermissionEvaluatorHandler;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.task.TaskService;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.HandlerName;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserPrivilege;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserRolePrivilege;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class TaskPermissionEvaluatorHandler implements PermissionEvaluatorHandler {

    private final static String TARGET_TYPE = HandlerName.task.path;

    private final TaskService taskService;
    private final TaskUserRolePrivilege taskUserRolePrivilege;

    public TaskPermissionEvaluatorHandler(TaskService taskService, TaskUserRolePrivilege taskUserRolePrivilege) {
        this.taskService = taskService;
        this.taskUserRolePrivilege = taskUserRolePrivilege;
    }

    @Override
    public String getKey() {
        return TARGET_TYPE;
    }

    @Override
    public boolean hasPermission(User user, Long targetId, Object permission) {

        AtomicBoolean result = new AtomicBoolean(false);

        TaskUserPrivilege privilege = (TaskUserPrivilege)permission;

        // 1. check user role on this task
        taskService.getTaskMemberRole(targetId, user)
            .ifPresent(taskUserRole -> {

                // check if taskUserRole contain requested permission(privilege)
                if (taskUserRolePrivilege.getRolePrivileges().get(taskUserRole).contains(privilege)) {
                    result.set(true);
                }
            });

        return result.get();
    }
}
