package ru.geekbrains.handmade.ltmbackend.core.configurations.security.acl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.task.TaskService;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.HandlerName;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserPrivilege;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserRolePrivilege;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Component("permissionEvaluator")
@Slf4j
public class PermissionEvaluator implements org.springframework.security.access.PermissionEvaluator {

    private final UserService userService;

    private final Map<String, PermissionEvaluatorHandler> permissionEvaluatorHandlers;



    public PermissionEvaluator(UserService userService,
                               List<PermissionEvaluatorHandler> permissionEvaluatorHandlers) {

        this.userService = userService;
        this.permissionEvaluatorHandlers = permissionEvaluatorHandlers.stream()
            .collect(Collectors.toMap(PermissionEvaluatorHandler::getKey, eh -> eh));

    }

    /**
     * @param authentication     represents the user in question. Should not be null.
     * @param targetDomainObject the domain object for which permissions should be
     *                           checked. May be null in which case implementations should return false, as the null
     *                           condition can be checked explicitly in the expression.
     * @param permission         a representation of the permission object as supplied by the
     *                           expression system. Not null.
     * @return true if the permission is granted, false otherwise
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        log.info("ENTER hasPermission1 task id:{},  privilege: {}", targetDomainObject, permission);

        if (authentication != null && permission instanceof String) {

            User user = userService.getCurrent();
            //User loggedUser = (User) authentication.getPrincipal();

            String permissionToCheck = (String) permission;
            // in this part of the code you need to check if the loggedUser has the "permission" over the
            // targetDomainObject. In this implementation the "permission" is a string, for example "read", or "update"
            // The targetDomainObject is an actual object, for example a object of UserProfile class (a class that
            // has the profile information for a User)

            // You can implement the permission to check over the targetDomainObject in the way that suits you best
            // A naive approach:
//            if (targetDomainObject.getClass().getSimpleName().compareTo("UserProfile") == 0) {
//                if ((UserProfile) targetDomainObject.getId() == loggedUser.getId())
//                    return true;
//            }

            // A more robust approach: you can have a table in your database holding permissions to each user over
            // certain targetDomainObjects
//            List<Permission> userPermissions = permissionRepository.findByUserAndObject(loggedUser,
//                targetDomainObject.getClass().getSimpleName());
            // now check if in userPermissions list we have the "permission" permission.

            // ETC...
        }
        //access denied
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {

        boolean result = false;

        log.info("ENTER hasPermission2 type {} task id:{},  privilege: {}", targetType, targetId, permission);

        if (authentication != null) {


            if(permissionEvaluatorHandlers.containsKey(targetType)) {
                result = permissionEvaluatorHandlers.get(targetType).hasPermission(
                    userService.getCurrent(),
                    (Long)targetId,
                    permission);
            }
        }
        return result;
    }

    //    @Override
//    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
//        return false;
//    }
//
//    @Override
//    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
//        return false;
//    }
}