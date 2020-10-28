package ru.geekbrains.handmade.ltmbackend.tests.system_test;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.geekbrains.handmade.ltmbackend.tests.system_test.utils.UserCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SystemTestInitializer implements ApplicationRunner {

    @Autowired
    private UserCreator userCreator;

    // will run before tests
    @Override
    public void run(ApplicationArguments args) throws JsonProcessingException {

        //userCreator.cre
        //userCreator.createCourierUser();
    }
}

