package ru.geekbrains.handmade.ltmbackend.core.task;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
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


    @Test
    @Order(0)
    void createTasks() {
        User user = userService.findByEmail("vasya@mail.ru")
            .orElseThrow(() -> new UsernameNotFoundException("user 'vasya@mail.ru' не найден"));

        //Optional<User> opUser = userService.findByEmail("vasya@mail.ru");
        //Assertions.assertThat(opUser).isPresent().info.description("No user 'vasya@mail.ru' was found");

        Task task = new Task("Task1", null, user);

        User subUser = new User("subuser", "******",
            "Subuser", "Sub", 33, "sub@mail.ru", "32101");

        userService.save(subUser);

        User execUser = new User("executor", "******",
            "Exe", "Cutor", 45, "exec@mail.ru", "76868");

        userService.save(execUser);

        task.addMember(subUser, TaskUserRole.REGULAR);
        task.addMember(execUser, TaskUserRole.EXECUTOR);

        Task subtask = new Task("Task1Subtask1", task, subUser);
        subtask.addMember(userService.findByUsername("sema").get(), TaskUserRole.REGULAR);

        //new Task("Task1Subtask1Subtask2", subtask, subUser);
        
        task = taskService.save(task); // cascade save all inner stuff
        long taskId = task.getId();

        Long subtaskId = subtask.getId();
        log.debug("subtaskId == {}", subtaskId);
        Assertions.assertNotNull(subtaskId, "Subtask id == null");

        Task tt = taskService.findByIdOrError(taskId);

        //Task tt = taskService.findByIdZeta(taskId).get();
        
        taskService.truncateLazy(tt);
        System.out.println(tt);
    }

    @Test
    @Order(1)
    void editTask() {
        User user = userService.findByEmail("vasya@mail.ru")
            .orElseThrow(() -> new UsernameNotFoundException("user 'vasya@mail.ru' не найден"));

        Task task = taskService.findByIdOrError(1L);

        //Task tt = taskService.findByIdZeta(taskId).get();

        taskService.truncateLazy(task);
        System.out.println(task);
    }


}
