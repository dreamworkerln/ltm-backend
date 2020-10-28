package ru.geekbrains.handmade.ltmbackend.ltmapplication.controllers.order;

import ru.geekbrains.handmade.ltmbackend.core.controllers.jrpc.annotations.JrpcController;
import ru.geekbrains.handmade.ltmbackend.core.controllers.jrpc.annotations.JrpcMethod;
import ru.geekbrains.handmade.ltmbackend.core.converters.order.OrderConverter;
import ru.geekbrains.handmade.ltmbackend.core.entities.Order;
import ru.geekbrains.handmade.ltmbackend.core.services.order.OrderClientService;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.HandlerName;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.order.OrderDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.order.OrderSpecDto;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.UserRole;
import org.springframework.security.access.annotation.Secured;
import java.util.List;


@JrpcController(HandlerName.order.client.path)
@Secured(UserRole.VAL.CLIENT)
public class OrderClientController {



    private final OrderConverter converter;
    private final OrderClientService orderClientService;

    public OrderClientController(OrderConverter converter, OrderClientService orderClientService) {
        this.converter = converter;
        this.orderClientService = orderClientService;
    }

    /**
     * Get Order by id, filtered by orders belonged only to current client
     * @param params Long id
     * @return OrderDto
     */
    @JrpcMethod(HandlerName.order.client.findById)
    public OrderDto findById(Long id) {

        Order order = orderClientService.findById(id).orElse(null);
        return converter.toDto(order);
    }


    /**
     * Get all my Orders, filtered by orders belonged only to current client
     * @param params OrderSpecDto/null
     * @return {@code List<OrderDto>}
     */
    @JrpcMethod(HandlerName.order.client.findAll)
    public List<OrderDto> findAll(OrderSpecDto specDto) {

        List<Order> orderList =  orderClientService.findAll(specDto);
        return converter.toDtoList(orderList);
    }


    /**
     * Save new created Order
     * @param params OrderDto
     * @return Long orderId
     */
    @JrpcMethod(HandlerName.order.client.save)
    public Long save(OrderDto orderDto) {

        Order order = converter.toEntity(orderDto);
        order = orderClientService.save(order);
        return order.getId();
    }


    /**
     * Delete order if its status == NEW
     * @param params Long orderId
     * @return Long deleted orderId
     */
    @JrpcMethod(HandlerName.order.client.cancel)
    public Long cancel(Long id) {

        Order order = orderClientService.cancel(id);
        return order.getId();
    }


    // ==================================================================================


}
