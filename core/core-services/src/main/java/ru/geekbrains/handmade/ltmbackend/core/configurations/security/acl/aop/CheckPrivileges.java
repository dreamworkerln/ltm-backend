package ru.geekbrains.handmade.ltmbackend.core.configurations.security.acl.aop;

import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserPrivilege;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPrivileges {

    //    /**
//     * SPEL Expression
//     *
//     * @return
//     */
//    String key() default "";
//
//    /**
//     * 1: Main Account, 2: Sub Account
//     */
//    int userType() default 1;
    // SPEL Expression, extract id

    // Target entity class
    Class<?> targetClass();
    // Target entity id, SPEL
    String targetId() default "";
    //TaskUserPrivilege[] permission();
    // requested permissions
    String[] permission();

}
