package ru.geekbrains.handmade.ltmbackend.core.basic;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphs;
import ru.geekbrains.handmade.ltmbackend.core.entities.*;
import ru.geekbrains.handmade.ltmbackend.core.services.task.TaskService;
import ru.geekbrains.handmade.ltmbackend.utils.Junit5Extension;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.CurrencyCode;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.OrderStatus;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.pivovarit.function.ThrowingRunnable.unchecked;


@SpringBootTest
@ExtendWith({Junit5Extension.class})
@Slf4j
@SuppressWarnings({"OptionalGetWithoutIsPresent"})
class LtmCoreTests {

    @Autowired
    ApplicationContext context;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;


//    @BeforeAll
//    private static void beforeAll() {
//        AbstractContainerBaseTest.init();
//    }

    // Should start first  and init users for others tests
    @Test
    @org.junit.jupiter.api.Order(-1000)
    void basicTest() {

        // TESTING LOG LEVELS
        log.info("TESTING LOG LEVELS");

        log.trace("TRACE");
        log.debug("DEBUG");
        log.info("INFO");
        log.warn("WARN");
        log.error("ERROR");
        log.info("");

        // TESTING DB

        //Account acc = new Account();
//        User user = new User("vasya", "INVALID",
//            "Залипов", "Вася", "vasya@mail.ru", "123");
        User user = userService.findByEmail("vasya@mail.ru")
            .orElseThrow( () -> new UsernameNotFoundException("user 'vasya@mail.ru' не найден"));
        //user.setAccount(acc);
        //userService.save(user);
        log.info("user id: {}", user.getId());

        User finalUser = user;
        userService.findByFullName(user.getLastName(), user.getFirstName())
            .orElseThrow( () -> new UsernameNotFoundException(finalUser.getFullName() + " не найден"));

        user = userService.findById(user.getId(), EntityGraphs.named(User.FULL_ENTITY_GRAPH)).get();
        Assert.assertNotNull(user.getRefreshTokenList());

        user = new User("testsema", "INVALID",
            "Семенов", "Тест", 23, "testsema@mail.ru", "456678test");
        userService.save(user);
    }


//    @Test
//    void checkTableRowLock() throws Exception {
//
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//
//        Account finalAcc1 = accountService.findById(1L).get();
//        Account finalAcc2 = accountService.findById(1L).get();
//        Future<?> f1 = executor.submit(unchecked(() ->
//            accountService.addBalance(finalAcc1, BigDecimal.valueOf(100), CurrencyCode.RUB)));
//        Future<?> f2 = executor.submit(unchecked(() ->
//            accountService.addBalance(finalAcc2, BigDecimal.valueOf(100), CurrencyCode.RUB)));
//
//        f1.get();
//        f2.get();
//        Account acc = accountService.findById(1L).get();
//
//        log.info("баланс: {}", acc.getBalance());
//    }
}
