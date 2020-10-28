package ru.geekbrains.handmade.ltmbackend.mail;

import ru.geekbrains.handmade.ltmbackend.utils.data.enums.CurrencyCode;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class MailTestInitializer implements ApplicationRunner {

    private final UserService userService;

    public MailTestInitializer(UserService userService) {
        this.userService = userService;
    }


    // will run before tests
    @Override
    public void run(ApplicationArguments args) {

        User user = new User("vasya", "INVALID",
                "Пупкин", "Вася", 56,"ltmbackendgeek@gmail.com","123");
        userService.save(user);
    }
}

