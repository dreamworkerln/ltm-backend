package ru.geekbrains.handmade.ltmbackend.core.task;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.geekbrains.handmade.ltmbackend.core.converters.address.AddressConverter;
import ru.geekbrains.handmade.ltmbackend.core.converters.task.TaskConverter;
import ru.geekbrains.handmade.ltmbackend.core.converters.user.UserConverter;
import ru.geekbrains.handmade.ltmbackend.core.entities.Address;
import ru.geekbrains.handmade.ltmbackend.core.entities.base.AbstractEntity;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.Task;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.AddressService;
import ru.geekbrains.handmade.ltmbackend.core.services.task.TaskService;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.address.AddressDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task.TaskDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task.TaskMemberDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.user.UserDto;
import ru.geekbrains.handmade.ltmbackend.utils.Junit5Extension;
import ru.geekbrains.handmade.ltmbackend.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserRole;


import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@SpringBootTest
@ExtendWith({Junit5Extension.class})
@Slf4j
@Disabled
class TaskConverterTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskConverter taskConverter;

//    @BeforeAll
//    private static void beforeAll() {
//        AbstractContainerBaseTest.init();
//    }

//    @PostConstruct
//    private void postConstruct() {
//        log.info("Checking Converter an Mapper logic");
//    }

    @Test
    void checkTaskConvention() {

        String username = "user";
        User userEntity = userService.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

        UserDto user = userConverter.toDto(userEntity);


        // create new task
        TaskDto task = new TaskDto();
        task.setTitle("Task01");
        task.setDeadline(Instant.now().plus(10, ChronoUnit.MINUTES));
        task.getMembers().add(new TaskMemberDto(task, user, TaskUserRole.OWNER));

        // convert task
        Task taskEntity = taskConverter.toEntity(task);
        taskEntity = taskService.save(taskEntity);
        Long taskId = taskEntity.getId();
        Assertions.assertNotNull(taskId, "New saved task id == null");
        System.out.println(taskEntity);
    }



}
