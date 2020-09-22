package ru.geekbrains.handmade.ltmbackend.core.converters;

import com.fasterxml.jackson.databind.JsonNode;
import ru.geekbrains.handmade.ltmbackend.core.converters.order.OrderConverter;
import ru.geekbrains.handmade.ltmbackend.core.entities.base.AbstractEntity;
import ru.geekbrains.handmade.ltmbackend.core.entities.Order;
import ru.geekbrains.handmade.ltmbackend.core.services.AddressService;
import ru.geekbrains.handmade.ltmbackend.core.services.order.OrderService;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.utils.Junit5Extension;
import ru.geekbrains.handmade.ltmbackend.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;


import javax.annotation.PostConstruct;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@SpringBootTest
@ExtendWith({Junit5Extension.class})
@Slf4j
class ConverterTests {

    @Autowired
    private OrderConverter orderConverter;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressService addressService;

//    @BeforeAll
//    private static void beforeAll() {
//        AbstractContainerBaseTest.init();
//    }

    @PostConstruct
    private void postConstruct() {
        log.info("Checking Converter an Mapper logic");
    }


    @Test
    void OrderConverterTest() {

        log.info("Checking OrderConverter");

        Order order = orderService.findById(1L).get();

        JsonNode orderJson = orderConverter.toDtoJson(order);
        String json = orderJson.toString();
        log.info(orderJson.toPrettyString());
        Order newWorldOrder = orderConverter.toEntity(orderJson);

//        copyTimes(newWorldOrder, order);
//        copyTimes(newWorldOrder.getFrom(), order.getFrom());
//        copyTimes(newWorldOrder.getTo(), order.getTo());

        JsonNode orderJsonNew = orderConverter.toDtoJson(order);
        String jsonNew = orderJson.toString();
        log.info(orderJsonNew.toPrettyString());

        Assert.isTrue(json.equals(jsonNew), "OrderConverter failed");
    }



    private void copyTimes(AbstractEntity entity, AbstractEntity from) {

        Utils.fieldSetter("created", entity, from.getCreated());
        Utils.fieldSetter("updated", entity, from.getUpdated());

    }
}