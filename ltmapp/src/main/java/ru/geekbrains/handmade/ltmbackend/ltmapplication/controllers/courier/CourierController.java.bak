package ru.geekbrains.handmade.ltmbackend.ltmapplication.controllers.courier;

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



/**
 * Courier management
 */
@JrpcController(HandlerName.courier.path)
public class CourierController {

    private final CourierService courierService;
    private final UserService userService;
    private final CourierConverter converter;

    public CourierController(CourierService courierService, UserService userService, CourierConverter converter) {
        this.courierService = courierService;
        this.userService = userService;
        this.converter = converter;
    }


    /**
     * Return current courier
     * @param params null
     * @return CourierDto
     */
    @JrpcMethod(HandlerName.courier.getCurrent)
    @Secured(UserRole.VAL.COURIER)
    public CourierDto getCurrent() {

        User user = userService.getCurrent();
        Courier client = courierService.findOneByUser(user).orElse(null);
        return converter.toDto(client);
    }


}
