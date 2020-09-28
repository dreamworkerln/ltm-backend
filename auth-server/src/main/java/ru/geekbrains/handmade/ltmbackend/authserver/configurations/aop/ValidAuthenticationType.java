package ru.geekbrains.handmade.ltmbackend.authserver.configurations.aop;

import ru.geekbrains.handmade.ltmbackend.authserver.configurations.AuthType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify allowed authentication types
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidAuthenticationType {
    AuthType[] value();
}