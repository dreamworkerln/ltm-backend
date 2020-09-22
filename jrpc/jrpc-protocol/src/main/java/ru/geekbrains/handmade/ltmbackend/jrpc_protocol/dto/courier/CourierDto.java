package ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.courier;

import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.AbstractDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.user.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CourierDto extends AbstractDto {

    private UserDto user;
    private String courierSpecificData;

    public CourierDto(UserDto user, String courierSpecificData) {
        this.user = user;
        this.courierSpecificData = courierSpecificData;
    }
}
