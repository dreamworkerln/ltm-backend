package ru.geekbrains.handmade.ltmbackend.ltmapplication;

import ru.geekbrains.handmade.ltmbackend.core.UsersInitializer;
import ru.geekbrains.handmade.ltmbackend.ltmapplication.entities.TestEntity;
import ru.geekbrains.handmade.ltmbackend.ltmapplication.repositories.TestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LtmInitializer implements ApplicationRunner {

    private final TestRepository testRepository;
    private final UsersInitializer usersInitializer;

    @Autowired
    public LtmInitializer(TestRepository testRepository,
                          UsersInitializer usersInitializer) {

        this.testRepository = testRepository;
        this.usersInitializer = usersInitializer;
    }

    @Override
    public void run(ApplicationArguments args) {

        //log.info("LtmAppDemoInitializer - add basic entities to DB");

        testRepository.save(new TestEntity("Вася test"));

        // ASAP EDC
        // TODO REMOVE ME!!!!
        usersInitializer.initUsers();

//        // Only for Tests purposes on H2 database !!!
//        // to handle that each microservice has separate H2 instance
//        // Should't be run on production !
//        log.debug("AuthServer initialize users");
//        usersInitializer.initUsers();
    }

}

