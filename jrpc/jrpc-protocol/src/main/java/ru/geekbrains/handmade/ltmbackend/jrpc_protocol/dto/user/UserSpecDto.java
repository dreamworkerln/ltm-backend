package ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.user;

import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.AbstractSpecDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.client.ClientDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.courier.CourierDto;
import lombok.Data;

@Data
public class UserSpecDto extends AbstractSpecDto {

    public enum OrderBy {ASC,DESC}

    // null - выдать сразу все заказы, иначе по сколько отдавать
    private Integer limit;

    // принадлежность курьеру
    private CourierDto courier;

    // принадлежность клиенту
    private ClientDto client;
}
