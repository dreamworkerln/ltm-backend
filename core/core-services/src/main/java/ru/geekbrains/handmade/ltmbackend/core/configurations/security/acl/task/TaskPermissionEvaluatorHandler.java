package ru.geekbrains.handmade.ltmbackend.core.configurations.security.acl.task;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import ru.geekbrains.handmade.ltmbackend.core.configurations.security.acl.PermissionEvaluatorHandler;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.task.TaskService;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.UserRole;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserPrivilege;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserRolePrivilegeDictionary;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.Task;


/**
 * ACL Task handler
 */
@Component
public class TaskPermissionEvaluatorHandler implements PermissionEvaluatorHandler {

    private final static Class<?> TARGET_CLASS = Task.class;

    private final TaskService taskService;
    private final TaskUserRolePrivilegeDictionary taskPrivilegeDictionary;

    public TaskPermissionEvaluatorHandler(TaskService taskService, TaskUserRolePrivilegeDictionary taskPrivilegeDictionary) {
        this.taskService = taskService;
        this.taskPrivilegeDictionary = taskPrivilegeDictionary;
    }

    @Override
    public String getKey() {
        return TARGET_CLASS.getName();
    }

    /**
     *  Если User имеет роль Manager, то он может производить любые действия над любым заданием
     */
    @Override
    public boolean hasPermission(User user, Long targetId, Set<String> privileges) {

        AtomicBoolean result = new AtomicBoolean(false);

        // Manager'y можно все
        if (user.getRoles().contains(UserRole.MANAGER)) {
            result.set(true);
        }
        else {
            // Проверяем, содержится ли запрошенная клиентом привилегия(привилегии)
            // В наборе предопределенных привилегий для роли пользователя в контексте указанной задачи.

            // 1. Узнаем роль у текущего пользователя по указанной задаче
            taskService.getTaskMemberRole(targetId, user)
                .ifPresent(taskUserRole -> {

                    // Получаем набор (предопределенных) привилегий для этой роли
                    Set<TaskUserPrivilege> userRolePrivileges = taskPrivilegeDictionary.getRolePrivileges().get(taskUserRole);

                    // Проверяем, что в роли пользователя по указанной задачи
                    // содержатся запрашиваемые привилегии
                    for (String p : privileges) {

                        // переводим привилегию из String в Enum
                        TaskUserPrivilege requestedPrivilege = TaskUserPrivilege.getByName(p);
                        if (!userRolePrivileges.contains(requestedPrivilege)) {
                            break;
                        }
                        // finally all is OK
                        result.set(true);
                    }
                });
        }

        return result.get();
    }
}
