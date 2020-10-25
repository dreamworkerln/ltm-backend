package ru.geekbrains.handmade.ltmbackend.core.task;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.Task;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.task.TaskService;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.utils.Junit5Extension;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserRole;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@SpringBootTest
@ExtendWith({Junit5Extension.class})
@Slf4j
class TaskTest {


    //@Autowired
    //ApplicationContext context;
    //@Autowired
    //private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;


    private void addSomeUsers() {

        User user;
        // vasya/vasya_password
        if (userService.findByUsername("pupkinvasya").isEmpty()) {
            user = new User("pupkinvasya", "{bcrypt}$2a$10$ptWulW3vFICm8Pu.CmulbuNx1GsgwO8UHrcZuVJi22mF792qRxjMu",
                "Вася", "Пупкин", 37, "vasya@mail.ru", "1122334455");

            userService.save(user);
        }

        // sema/sema_password
        if (userService.findByUsername("sema").isEmpty()) {
            user = new User("sema", "{bcrypt}$2a$10$zqdgSPaIehsb82r7psbBKOU5bkfCo8pqv9BwuwLz5BoEcSXQuqdnW",
                "Сема", "Пасечкин",null, "sema@mail.ru", "908796786543");
            userService.save(user);
        }
    }

    @Test
    void createTasks() {

        addSomeUsers();

        User user = userService.findByEmail("vasya@mail.ru")
            .orElseThrow(() -> new UsernameNotFoundException("user 'vasya@mail.ru' не найден"));

        //Optional<User> opUser = userService.findByEmail("vasya@mail.ru");
        //Assertions.assertThat(opUser).isPresent().info.description("No user 'vasya@mail.ru' was found");

        Task task = new Task("Task1", null, user);

        User subUser = new User("subuser", "******",
            "Subuser", "Sub", 33, "sub@mail.ru", "32101");

        userService.save(subUser);

        User execUser = new User("executer", "******",
            "Exe", "Cutor", 45, "exec@mail.ru", "76868");

        userService.save(execUser);

        task.addMember(subUser, TaskUserRole.REGULAR);
        task.addMember(execUser, TaskUserRole.EXECUTOR);

        Task subtask = new Task("Task1subtask1", task, subUser);


        subtask.addMember(userService.findByUsername("sema").get(), TaskUserRole.REGULAR);
        taskService.save(task); // cascade save all inner stuff

        Long subtaskId = subtask.getId();
        Assertions.assertNotNull(subtaskId, "Subtask id == null");
    }
}
