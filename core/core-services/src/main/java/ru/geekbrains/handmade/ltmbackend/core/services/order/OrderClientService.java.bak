package ru.geekbrains.handmade.ltmbackend.core.services.order;

import ru.geekbrains.handmade.ltmbackend.core.converters.client.ClientConverter;
import ru.geekbrains.handmade.ltmbackend.core.converters.order.OrderConverter;
import ru.geekbrains.handmade.ltmbackend.core.entities.Order;
import ru.geekbrains.handmade.ltmbackend.core.services.ClientService;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.client.ClientDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.order.OrderSpecDto;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Secured(UserRole.VAL.CLIENT)
@Slf4j
public class OrderClientService {

    private final OrderService orderService;
    private final ClientService clientService;
    private final ClientConverter clientConverter;
    private final OrderConverter orderConverter;

    public OrderClientService(OrderService orderService, ClientService clientService,
                              ClientConverter clientConverter, OrderConverter orderConverter) {
        this.orderService = orderService;
        this.clientService = clientService;
        this.clientConverter = clientConverter;
        this.orderConverter = orderConverter;
    }



    public Optional<Order> findById(Long id) {

        OrderSpecDto specDto = filterOrderShowOnlyMine(null);
        specDto.setId(id);
        Specification<Order> spec = orderConverter.buildSpec(specDto);
        return orderService.findOne(spec);
    }

    public List<Order> findAll(OrderSpecDto specDto) {

        specDto = filterOrderShowOnlyMine(specDto);
        Specification<Order> spec =  orderConverter.buildSpec(specDto);
        return orderService.findAll(spec);
    }

    public Order save(Order order) {

        order.setClient(clientService.getCurrent());
        checkOrderOwner(order.getId());
        return orderService.save(order);
    }

    public Order cancel(Long id) {

        checkOrderOwner(id);

        throw new NotImplementedException();
    }



    // ==============================================================================


    private OrderSpecDto filterOrderShowOnlyMine(OrderSpecDto specDto) {

        OrderSpecDto result = specDto;
        // assign current courier to OrderSpecDto
        if(result == null) {
            result = new OrderSpecDto();
        }
        ClientDto clientDto = clientConverter.toDto(clientService.getCurrent());
        result.setClient(clientDto);
        return result;
    }

    /**
     * Check that currently being modified Order is belong to current Client
     * @param id Order Id
     */
    private void checkOrderOwner(Long id) {

        // order was persisted
        if(id != null) {
            Order old = orderService.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Order with id:\n" + id + " not exists"));

            // client try to modify foreign Order (belonged to other client)
            if (!old.getClient().equals(clientService.getCurrent())) {
                throw new AccessDeniedException("HAAKCEEER GTFO !!!");
            }
        }
    }
}


// @SuppressWarnings("DuplicatedCode")
