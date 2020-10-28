package ru.geekbrains.handmade.ltmbackend.ltmapplication.controllers.user;


import ru.geekbrains.handmade.ltmbackend.core.controllers.jrpc.annotations.JrpcController;
import ru.geekbrains.handmade.ltmbackend.core.controllers.jrpc.annotations.JrpcMethod;
import ru.geekbrains.handmade.ltmbackend.core.converters.user.UserConverter;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.HandlerName;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.user.UserDto;
import ru.geekbrains.handmade.ltmbackend.utils.StringUtils;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.UserRole;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;


/**
 * Allow manage user properties by user themselves
 */
@JrpcController(HandlerName.user.path)
@Secured(UserRole.VAL.USER)
public class UserController {

    private final UserService userService;
    private final UserConverter converter;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UserConverter converter, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.converter = converter;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * Return current user
     * @return UserDto
     */
    @JrpcMethod(HandlerName.user.getCurrent)
    public UserDto getCurrent() {

        User user = userService.getCurrent();
        return converter.toDto(user);
    }

    /**
     * Update user data (password, payPalEmail)
     * <br> Will not save firstName, lastName, account, UserRoles, client, courier, refreshTokenList
     * @param params Userdto
     * @return Long userId
     */
    @JrpcMethod(HandlerName.user.save)
    public Long save(UserDto userDto) {

        User user = converter.toEntity(userDto);

        // Check that userDto have same username, mail, phone
        if(!user.equals(userService.getCurrent())) {
            throw new IllegalArgumentException("Invalid user params. Username, mail, phone should be the same");
        }

        // ToDo:  тут нужна двухфакторная авторизация, чтобы не угнали аккаунт и сменили пароль
        // update password - if not empty and not already bcrypted(loaded from DB)
        if (!StringUtils.isBlank(user.getPassword()) &&
            !user.getPassword().contains("{bcrypt}")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Security ---------------------------------
        User currentUser = userService.getCurrent();
        // preserve roles
        user.setRoles(currentUser.getRoles());
        // preserve enabled
        user.setEnabled(currentUser.isEnabled());
        // ------------------------------------------

        // finally save
        user = userService.save(user);
        return user.getId();
    }
}


//        // Security, update confidential data from DB
//        user.getRoles().clear(); // Clear roles from DTO
//        user.setAccount(null);   // Clear account from DTO
//        User result = userService.getCurrent();
//        SpringBeanUtilsEx.CopyPropertiesExcludeNull(user, result);
