package ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.order;

import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.AbstractDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.address.AddressDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.client.ClientDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.courier.CourierDto;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDto extends AbstractDto {

    private ClientDto client;
    private CourierDto courier;

    private AddressDto from;
    private AddressDto to;

    private OrderStatus status;

    // moar info ...
}
