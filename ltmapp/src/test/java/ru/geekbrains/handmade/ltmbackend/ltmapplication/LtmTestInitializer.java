package ru.geekbrains.handmade.ltmbackend.ltmapplication;

import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LtmTestInitializer implements ApplicationRunner {

    private final UserService userService;

    public LtmTestInitializer(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void run(ApplicationArguments args) {

        // Prepare database here

        log.info("LtmTestInitializer - add basic entities to DB for tests");
        User user;


        // Prepare database here

        try {
            /*
            from = new Address("Москва", "Улица красных тюленей", 1, 2, 3);
            to = new Address("Мухосранск", "Западная", 2, 2, 5);

            user = new User("vasya1", "INVALID",
                "Вася1", "Пупкин1", 56, "vasya1@mail.ru", "112232344551");
            userService.save(user);

            client = new Client(user, "CLIENT_DATA");
            clientService.save(client);

            user = new User("sema1", "INVALID",
                "Сема1", "Пасечкин", 56,"sema1@mail.ru", "9087967865431");
            userService.save(user);
            courier = new Courier(user, "COURIER_DATA");
            courierService.save(courier);
            order = new Order();
            order.setFrom(from);
            order.setTo(to);
            order.setStatus(OrderStatus.COMPLETED);
            order.setCourier(courier);
            order.setClient(client);
            orderService.save(order);
            */
        }
        catch (Exception e){
            log.error("LtmTestInitializer init test error:", e);
        }
    }
}

