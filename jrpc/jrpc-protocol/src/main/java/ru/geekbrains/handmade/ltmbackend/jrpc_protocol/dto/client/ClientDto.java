package ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.client;

import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.AbstractDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.user.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ClientDto extends AbstractDto {

    private UserDto user;
    private String clientSpecificData;

    public ClientDto(UserDto user, String clientSpecificData) {
        this.user = user;
        this.clientSpecificData = clientSpecificData;
    }
}
