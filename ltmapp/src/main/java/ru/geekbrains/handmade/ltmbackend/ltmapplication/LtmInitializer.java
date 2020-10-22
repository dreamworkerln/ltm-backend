package ru.geekbrains.handmade.ltmbackend.ltmapplication;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.geekbrains.handmade.ltmbackend.core.UsersInitializer;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.Task;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.task.TaskService;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.ltmapplication.entities.TestEntity;
import ru.geekbrains.handmade.ltmbackend.ltmapplication.repositories.TestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserRole;

@Component
@Slf4j
public class LtmInitializer implements ApplicationRunner {

    private final TestRepository testRepository;
    private final UsersInitializer usersInitializer;

    // ToDo: remove after debug
    private final UserService userService;
    private final TaskService taskService;

    @Autowired
    public LtmInitializer(TestRepository testRepository,
                          UsersInitializer usersInitializer, UserService userService, TaskService taskService) {

        this.testRepository = testRepository;
        this.usersInitializer = usersInitializer;
        this.userService = userService;
        this.taskService = taskService;
    }

    @Override
    public void run(ApplicationArguments args) {

        log.info("LtmInitializer started");

        testRepository.save(new TestEntity("Вася test"));

        // ASAP EDC
        // TODO REMOVE ME!!!!
        usersInitializer.initUsers();
        addSomeDebugTasks();




//        // Only for Tests purposes on H2 database !!!
//        // to handle that each microservice has separate H2 instance
//        // Should't be run on production !
//        log.debug("AuthServer initialize users");
//        usersInitializer.initUsers();

        log.info("LtmInitializer finished");
    }

    private void addSomeDebugTasks() {
        User user = userService.findByEmail("vasya@mail.ru")
            .orElseThrow( () -> new UsernameNotFoundException("user 'vasya@mail.ru' не найден"));

        //Optional<User> opUser = userService.findByEmail("vasya@mail.ru");
        //Assertions.assertThat(opUser).isPresent().info.description("No user 'vasya@mail.ru' was found");

        Task task = new Task("Task1", null, user);


        User subUser = new User("subuser", "INVALID",
            "Subuser", "Sub", 33, "sub@mail.ru", "32101");

        userService.save(subUser);


        User execUser = new User("executer", "",
            "Exe", "Cutor", 45, "exec@mail.ru", "76868");

        userService.save(execUser);

        task.addMember(subUser, TaskUserRole.REGULAR);
        task.addMember(execUser, TaskUserRole.EXECUTOR);

        Task subtask = new Task("Task1subtask1", task, subUser);
        subtask.addMember(user,TaskUserRole.REGULAR);
        taskService.save(task); // cascading
        //taskService.save(subtask);
        System.out.println("remove me - added some tasks for debug");
    }

}

