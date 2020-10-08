package ru.geekbrains.handmade.ltmbackend.core.entities.base;

import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsCustom extends UserDetails {
    // Better don't do this - user fields may change during request scope
    User getUser();
}
