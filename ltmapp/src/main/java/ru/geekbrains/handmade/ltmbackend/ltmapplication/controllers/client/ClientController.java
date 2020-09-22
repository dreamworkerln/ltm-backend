package ru.geekbrains.handmade.ltmbackend.ltmapplication.controllers.client;

import ru.geekbrains.handmade.ltmbackend.core.controllers.jrpc.annotations.JrpcController;
import ru.geekbrains.handmade.ltmbackend.core.controllers.jrpc.annotations.JrpcMethod;
import ru.geekbrains.handmade.ltmbackend.core.converters.client.ClientConverter;
import ru.geekbrains.handmade.ltmbackend.core.entities.Client;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.ClientService;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.HandlerName;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.client.ClientDto;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.UserRole;
import org.springframework.security.access.annotation.Secured;


// ToDo: перенести это все в управлялку ManagerClientController, кроме последнего метода

/**
 * Client management
 */
@JrpcController(HandlerName.client.path)
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;
    private final ClientConverter converter;

    public ClientController(ClientService clientService, UserService userService,
                            ClientConverter converter) {
        this.clientService = clientService;
        this.userService = userService;
        this.converter = converter;
    }


    /**
     * Return current client
     * @param params null
     * @return ClientDto
     */
    @JrpcMethod(HandlerName.client.getCurrent)
    @Secured(UserRole.VAL.CLIENT)
    public ClientDto getCurrent() {

        User user = userService.getCurrent();
        Client client = clientService.findOneByUser(user).orElse(null);
        return converter.toDto(client);
    }


}
