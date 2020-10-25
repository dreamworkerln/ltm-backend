package ru.geekbrains.handmade.ltmbackend.core;


import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Called from auth-server to create basic users
 * ToDo: now called from ltmapplication due to auth-server disabling
 */
@Component
@Slf4j
public class UsersInitializer {

    private final UserService userService;

    @Autowired
    public UsersInitializer(UserService userService) {
        this.userService = userService;
    }

    //@SuppressWarnings("OptionalGetWithoutIsPresent")
    public void initUsers() {

        User user;
        log.info("initialize default users");

        // admin user  --------------------------------------------------
        // root/toor
        if (userService.findByUsername("root").isEmpty()) {
            user = new User("root",
                "{bcrypt}$2a$10$SQUaDnIckmdPr1Wf/WOYiOL42yn0zCPHoM9qC3XNYsH9NyLqVbWKK",
                "root", "root", null, "root@mail.ru", "root");
            user.getRoles().add(UserRole.ADMIN);
            user.getRoles().add(UserRole.MANAGER);
            userService.save(user);
        }

        // user/user_password
        if (userService.findByUsername("user").isEmpty()) {
            user = new User("user", "{bcrypt}$2a$10$4oxoJzJti6NZmP.4d98VJue..tPH6otFLlgMTuA.nWHvtmnZRupE2",
                "user", "user", null,"user@mail.ru", "user");
            userService.save(user);
        }


//        // frontend user that register new users  ---------------------
//        // registrar/registrar_password
//        if (!userService.findByUsername("registrar").isPresent()) {
//            user = new User("registrar",
//                "{bcrypt}$2y$10$hEAtx1Hu3cBCb46umeGvUeH1PmyJI4pxDRTsUixto67JSH5W4VI4W",
//                "Registrar", "Registrar", "registrar@mail.ru", "registrar");
//            user.getRoles().add(userRoleService.findByName(UserRole.REGISTRAR));
//            userService.save(user);
//        }

        // demo users --------------------------------------------------


        log.info("initialize default users done");
    }
}
