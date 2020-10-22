package ru.geekbrains.handmade.ltmbackend.ltmapplication.controllers.management;

import ru.geekbrains.handmade.ltmbackend.core.converters.order.OrderConverter;
import ru.geekbrains.handmade.ltmbackend.core.entities.Order;
import ru.geekbrains.handmade.ltmbackend.core.services.order.OrderService;
import ru.geekbrains.handmade.ltmbackend.core.controllers.jrpc.annotations.JrpcController;
import ru.geekbrains.handmade.ltmbackend.core.controllers.jrpc.annotations.JrpcMethod;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.HandlerName;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.order.OrderDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.order.OrderSpecDto;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

@JrpcController(HandlerName.management.order.path)
@Secured(UserRole.VAL.MANAGER)
public class OrderManagerController {

    private final OrderService orderService;
    private final OrderConverter converter;

    @Autowired
    public OrderManagerController(OrderService orderService, OrderConverter converter) {
        this.orderService = orderService;
        this.converter = converter;
    }

    /**
     * Get Order by id
     * @param params Long id
     * @return
     */
    @JrpcMethod(HandlerName.management.order.findById)
    public OrderDto findById(Long id) {

        Order order = orderService.findById(id).orElse(null);
        return converter.toDto(order);
    }


    /**
     * Get List<Order> by idList
     * @param params List<Long> idList
     * @return
     */
    @JrpcMethod(HandlerName.management.order.findAllById)
    public List<OrderDto> findAllById(List<Long> idList) {

        List<Order> list = orderService.findAllById(idList);
        return converter.toDtoList(list);
    }

    /**
     * Get order by Specification
     * @param params OrderSpecDto
     * @return
     */
    @JrpcMethod(HandlerName.management.order.findAll)
    public List<OrderDto> findAll(OrderSpecDto specDto) {

        Specification<Order> spec = converter.buildSpec(specDto);
        return converter.toDtoList(orderService.findAll(spec));
    }

    //
    //  Return first ProductSpecDto.limit elements
    //

    /***
     * Get first limit elements by OrderSpecDto (with ~pagination)
     * <br> So, for example you got first 10 Orders, ordered by price.
     * Then set filter price greater than price in 10 Order and ask (next) 10 Orders and so on
     * Until you got no moar orderz
     * @param params
     * @return
     */
    @JrpcMethod(HandlerName.management.order.findFirst)
    public List<OrderDto> findFirst(OrderSpecDto specDto) {

        int limit = specDto != null ? specDto.getLimit() : 1;
        Specification<Order> spec = converter.buildSpec(specDto);
        Page<Order> page = orderService.findAll(spec, PageRequest.of(0, limit));
        return converter.toDtoList(page.toList());
    }


    /**
     * Save Order(insert new or update existing)
     * @param params Order
     * @return order.id     */
    @JrpcMethod(HandlerName.management.order.save)
    public Long save(OrderDto orderDto) {

        Order order = converter.toEntity(orderDto);
        order = orderService.save(order);
        return order.getId();
    }
    

    /**
     * Delete Order
     * @param OrderDto
     */
    @JrpcMethod(HandlerName.management.order.delete)
    public void delete(OrderDto orderDto) {
        
        Order order = converter.toEntity(orderDto);
        orderService.delete(order);
    }
}
