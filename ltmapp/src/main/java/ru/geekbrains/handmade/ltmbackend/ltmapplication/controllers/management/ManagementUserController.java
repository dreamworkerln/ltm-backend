package ru.geekbrains.handmade.ltmbackend.ltmapplication.controllers.management;


import ru.geekbrains.handmade.ltmbackend.core.controllers.jrpc.annotations.JrpcController;
import ru.geekbrains.handmade.ltmbackend.core.controllers.jrpc.annotations.JrpcMethod;
import ru.geekbrains.handmade.ltmbackend.core.converters.user.UserConverter;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.HandlerName;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.user.UserDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.user.UserSpecDto;
import ru.geekbrains.handmade.ltmbackend.utils.StringUtils;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


/**
 * Управление пользователями
 */

@JrpcController(HandlerName.management.user.path)
@Secured(UserRole.VAL.MANAGER)
public class ManagementUserController {


    private final UserService userService;
    private final UserConverter converter;
    private final PasswordEncoder passwordEncoder;

    public ManagementUserController(UserService userService, UserConverter converter,
                                    PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.converter = converter;
        this.passwordEncoder = passwordEncoder;
    }



    @JrpcMethod(HandlerName.management.user.findById)
    public UserDto findById(Long id) {

        User user = userService.findById(id).orElse(null);
        return converter.toDto(user);
    }


    /**
     * Get List<User> by idList
     * @param params List<Long> idList
     * @return List<UserDto>
     */
    @JrpcMethod(HandlerName.management.user.findAllById)
    public List<UserDto> findAllById(List<Long> idList) {

        List<User> list = userService.findAllById(idList);
        return converter.toDtoList(list);
    }


    /**
     * Find user by username
     * @param String username
     * @return UserDto
     */
    @JrpcMethod(HandlerName.management.user.findByUsername)
    public UserDto findByUsername(String username) {

        User user = userService.findByUsername(username).orElse(null);
        return converter.toDto(user);
    }



    /**
     * Find all users
     * @param params UserSpecDto
     * @return List<UserDto>
     */
    @JrpcMethod(HandlerName.management.user.findAll)
    public List<UserDto> findAll(UserSpecDto specDto) {

        Specification<User> spec =  converter.buildSpec(specDto);
        return converter.toDtoList(userService.findAll(spec));
    }



    /***
     * Get first limit elements by UserSpecDto (with ~pagination)
     * @return List<UserDto>
     */
    @JrpcMethod(HandlerName.management.user.findFirst)
    public List<UserDto> findFirst(UserSpecDto specDto) {

        int limit = specDto != null ? specDto.getLimit() : 1;
        Specification<User> spec =  converter.buildSpec(specDto);
        Page<User> page = userService.findAll(spec, PageRequest.of(0, limit));
        return converter.toDtoList(page.toList());
    }


    /**
     * Save User, may change roles
     * <br> Will not save Account
     * @param params userDto
     * @return
     */
    @JrpcMethod(HandlerName.management.user.save)
    public Long save(UserDto userDto) {

        User user = converter.toEntity(userDto);

        // update/set password - if not empty and not loaded from DB
        if (!StringUtils.isBlank(user.getPassword()) &&
            !user.getPassword().contains("{bcrypt}")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Security
        User currentUser = userService.getCurrent();
        // preserve Account
        user.setAccount(currentUser.getAccount());

        user = userService.save(user);
        return user.getId();
    }


    /**
     * Delete User
     * @param params
     * @return
     */
    @JrpcMethod(HandlerName.management.user.delete)
    public void delete(UserDto userDto) {

        User user = converter.toEntity(userDto);
        userService.delete(user);
    }

}
