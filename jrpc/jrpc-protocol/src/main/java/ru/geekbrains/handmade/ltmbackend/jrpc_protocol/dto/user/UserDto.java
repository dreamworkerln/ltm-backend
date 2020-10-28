package ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.user;

import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.AbstractDto;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.UserRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDto extends AbstractDto {

    private String username;
    private String password;
    private Set<UserRole> roles = new HashSet<>();

    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String phoneNumber;

    private String payPalEmail;

    @Override
    public String toString() {
        return "UserDto{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", roles=" + roles +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            '}';
    }
}
