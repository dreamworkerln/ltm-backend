package ru.geekbrains.handmade.ltmbackend.ltmapplication.controllers.manager;

import ru.geekbrains.handmade.ltmbackend.core.controllers.jrpc.annotations.JrpcController;
import ru.geekbrains.handmade.ltmbackend.core.controllers.jrpc.annotations.JrpcMethod;
import ru.geekbrains.handmade.ltmbackend.core.converters.courier.CourierConverter;
import ru.geekbrains.handmade.ltmbackend.core.entities.Courier;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.CourierService;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.HandlerName;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.courier.CourierDto;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.UserRole;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@JrpcController(HandlerName.manager.courier.path)
@Secured(UserRole.VAL.MANAGER)
public class CourierManagerController {

    private final CourierService courierService;
    private final UserService userService;
    private final CourierConverter converter;

    public CourierManagerController(CourierService courierService, UserService userService,
                                    CourierConverter converter) {
        this.courierService = courierService;
        this.userService = userService;
        this.converter = converter;
    }


    @JrpcMethod(HandlerName.manager.courier.findByUsername)
    public CourierDto findByUsername(String username) {

        Courier courier = courierService.findByUsername(username).orElse(null);;
        return converter.toDto(courier);
    }


    @JrpcMethod(HandlerName.manager.courier.save)
    public Long save(CourierDto courierDto) {


        Courier courier = converter.toEntity(courierDto);
        Long courierId = courier.getId();

        // check courier have user
        if(courier.getUser() == null) {
            throw new IllegalArgumentException("Courier without user");
        }
        long userId = courier.getUser().getId();

        // check courier user exists
        User user = userService.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("User by id " + userId + " not found"));

        // check user not stealed from other courier
        courierService.findOneByUser(user).ifPresent(c -> {
            if (courierId!= null && !c.getId().equals(courierId))
                throw new IllegalArgumentException("Stealing user: " + user.getUsername() + " from another courier: " + c.getId());
        });


        // assign COURIER role
        user.getRoles().add(UserRole.COURIER);
        userService.save(user);
        courier = courierService.save(courier);

        return courier.getId();
    }
}
