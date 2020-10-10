package ru.geekbrains.handmade.ltmbackend.mail;

import ru.geekbrains.handmade.ltmbackend.utils.data.enums.CurrencyCode;
import ru.geekbrains.handmade.ltmbackend.core.entities.Account;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.AccountService;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class MailTestInitializer implements ApplicationRunner {

    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public MailTestInitializer(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    // will run before tests
    @Override
    public void run(ApplicationArguments args) {

        Account account = new Account();
        User user = new User("vasya", "INVALID",
                "Пупкин", "Вася", 56,"ltmbackendgeek@gmail.com","123");
        user.setAccount(account);
        userService.save(user);

        accountService.addBalance(user.getAccount(), BigDecimal.TEN, CurrencyCode.USD);
    }
}

