package ru.geekbrains.handmade.ltmbackend.authserver.data;

import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.entities.base.UserDetailsCustom;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserPrincipalCustom implements UserDetailsCustom {

    private final User user;

    public UserPrincipalCustom(User user) {
        this.user = user;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(c -> new SimpleGrantedAuthority(c.getName())).collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {return user.getUsername();}

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isEnabled();
    }

    @Override
    public User getUser() {
        return user;
    }

    // -------------------------------------------------------------

    // Better don't do this - user fields  may change during request scope
    // public User getUser() { return user; }
}