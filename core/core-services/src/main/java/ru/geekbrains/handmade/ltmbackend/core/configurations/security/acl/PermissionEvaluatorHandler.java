package ru.geekbrains.handmade.ltmbackend.core.configurations.security.acl;

import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;

import java.io.Serializable;
import java.util.Set;

@Order(0) // - do not remove , or else @Order on other beans won't work
public interface PermissionEvaluatorHandler {

    String getKey();

    boolean hasPermission(User user,
                          Long targetId,
                          Set<String> privileges);
}
