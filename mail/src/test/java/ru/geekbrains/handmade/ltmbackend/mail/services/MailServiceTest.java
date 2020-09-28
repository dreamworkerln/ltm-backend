package ru.geekbrains.handmade.ltmbackend.mail.services;

import ru.geekbrains.handmade.ltmbackend.core.entities.user.UnconfirmedUser;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.utils.Junit5Extension;
import lombok.extern.slf4j.Slf4j;
import net.tascalate.concurrent.Promise;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.net.URI;
import java.util.concurrent.ExecutionException;

import static com.pivovarit.function.ThrowingSupplier.unchecked;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@SpringBootTest
@ExtendWith({Junit5Extension.class})
@Slf4j
class MailServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;



    @Test
    void userSendRegConfirmation() throws ExecutionException, InterruptedException {
        UnconfirmedUser user = new UnconfirmedUser("newuser",
            "newuser_password", "ltmbackendgeek@gmail.com", "Новый", "newuser@mail.ru", "932494356678");

        // отправляем письмо 
        URI uri =  unchecked(()->new URI("https://natribu.org/ru/")).get();

        Promise<Void> promise = mailService.sendRegistrationConfirmation(user, uri);
        promise.get();
        log.info("Message send");
    }


    @Test
    void sendPaymentSuccess() throws ExecutionException, InterruptedException {

        // отправляем письмо об успешном зачислении платежа
        User user = userService.findById(1L).get();
        Promise<Void> promise=mailService.sendPaymentSuccess(user, BigDecimal.valueOf(1337));
        promise.get();
        log.info("sendPaymentSuccess success");
    }
}
