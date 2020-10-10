package ru.geekbrains.handmade.ltmbackend.core.converters;

import com.fasterxml.jackson.databind.JsonNode;
import ru.geekbrains.handmade.ltmbackend.core.converters.address.AddressConverter;
import ru.geekbrains.handmade.ltmbackend.core.converters.order.OrderConverter;
import ru.geekbrains.handmade.ltmbackend.core.converters.user.UserConverter;
import ru.geekbrains.handmade.ltmbackend.core.entities.Address;
import ru.geekbrains.handmade.ltmbackend.core.entities.base.AbstractEntity;
import ru.geekbrains.handmade.ltmbackend.core.entities.Order;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.AddressService;
import ru.geekbrains.handmade.ltmbackend.core.services.order.OrderService;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.address.AddressDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.user.UserDto;
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
    private UserConverter userConverter;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressConverter addressConverter;

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

    @Test
    void AddressConverterTest() {

        log.info("Checking AddressConverter");

        Address address = new Address("Москва", "Улица желтых тюленей", 1, 2, 3);

        JsonNode addressJson = addressConverter.toDtoJson(address);
        String json = addressJson.toString();
        log.info(addressJson.toPrettyString());

        AddressDto addressDto = addressConverter.toDto(address);
        Address restoredAddress = addressConverter.toEntity(addressDto);
        System.out.println(restoredAddress);
    }


//    @Test
//    void UserConverterTest() {
//
//        log.info("Checking UserConverter");
//
//        User user = new User("1111111111111", "ghjghj",
//            "1111111111", "2222222222222", 56, "33333331@mail.ru", "5645546546546");
//
//        UserDto userDto = userConverter.toDto(user);
//        User restoredUser = userConverter.toEntity(userDto);
//        System.out.println(restoredUser);
//
//////        copyTimes(newWorldOrder, order);
//////        copyTimes(newWorldOrder.getFrom(), order.getFrom());
//////        copyTimes(newWorldOrder.getTo(), order.getTo());
////
////        JsonNode addressJsonNew = orderConverter.toDtoJson(order);
////        String jsonNew = orderJson.toString();
////        log.info(orderJsonNew.toPrettyString());
////
////        Assert.isTrue(json.equals(jsonNew), "OrderConverter failed");
//    }



    private void copyTimes(AbstractEntity entity, AbstractEntity from) {

        Utils.fieldSetter("created", entity, from.getCreated());
        Utils.fieldSetter("updated", entity, from.getUpdated());

    }
}