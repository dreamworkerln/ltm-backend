package ru.geekbrains.handmade.ltmbackend.tests.system_test.controllers.task;

import com.github.dockerjava.api.model.Task;
import org.junit.jupiter.api.*;
import ru.geekbrains.handmade.ltmbackend.jrpc_client.request.client.ClientRequest;
import ru.geekbrains.handmade.ltmbackend.jrpc_client.request.management.ManagementUserRequest;
import ru.geekbrains.handmade.ltmbackend.jrpc_client.request.payment.CashFlowRequest;
import ru.geekbrains.handmade.ltmbackend.jrpc_client.request.task.TaskRequest;
import ru.geekbrains.handmade.ltmbackend.jrpc_client.request.user.UserRequest;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.client.ClientDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.payment.CashFlowDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task.TaskDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.task.TaskMemberDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.user.UserDto;
import ru.geekbrains.handmade.ltmbackend.tests.system_test.configurations.SystemTestSpringConfiguration;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.CurrencyCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserRole;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskTests {

    @Autowired
    private SystemTestSpringConfiguration userConfig;
    @Autowired
    private UserRequest userRequest;
    @Autowired
    private TaskRequest taskRequest;
    @Autowired
    private ManagementUserRequest managementUserRequest;

    @Test
    @Order(1)
    void createAndFind() {

        log.info("createAndFind");

        // select user
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.USER);
        UserDto user = userRequest.getCurrent();

        // create new task
        TaskDto task = new TaskDto();
        task.setTitle("Task01");
        task.setDeadline(Instant.now().plus(10, ChronoUnit.MINUTES));
        task.getMembers().add(new TaskMemberDto(task, user, TaskUserRole.OWNER));

        // save task
        Long taskId = taskRequest.save(task);
        Assertions.assertNotNull(taskId, "New saved task id == null");
        System.out.println(task);


        // reload task from server, DDD
        TaskDto loadedTask = taskRequest.findById(taskId);
        System.out.println(loadedTask);
    }

    @Test
    @Order(2)
    void editExisted() {

        log.info("editExisted");

        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.ROOT);
        UserDto rootUser = managementUserRequest.findByUsername("root");

        // select user
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.USER);
        UserDto user = userRequest.getCurrent();

        // reload task from server, DDD
        TaskDto loadedTask = taskRequest.findById(1L);

        loadedTask.getMembers().removeIf(tm -> tm.getUserId() == 1);
        loadedTask.getMembers().add(new TaskMemberDto(loadedTask, rootUser, TaskUserRole.REGULAR));

        Long taskId = taskRequest.save(loadedTask);
        loadedTask = taskRequest.findById(taskId);
        System.out.println(loadedTask);
    }

    @Test
    @Order(3)
    void createOuterAndInner() {

        log.info("createOuterAndInner");

        // select user
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.USER);
        UserDto user = userRequest.getCurrent();

        // create new task
        TaskDto task = new TaskDto();
        task.setTitle("Task02");
        task.setDeadline(Instant.now().plus(10, ChronoUnit.MINUTES));
        task.getMembers().add(new TaskMemberDto(task, user, TaskUserRole.OWNER));

        TaskDto innerTask = new TaskDto();
        innerTask.setTitle("Task02Inner");
        innerTask.setDeadline(Instant.now().plus(10, ChronoUnit.MINUTES));
        innerTask.getMembers().add(new TaskMemberDto(task, user, TaskUserRole.OWNER));
        task.getSubtasks().add(innerTask);

        TaskDto innerInnerTask = new TaskDto();
        innerInnerTask.setTitle("Task03InnerInner");
        innerInnerTask.setDeadline(Instant.now().plus(10, ChronoUnit.MINUTES));
        innerInnerTask.getMembers().add(new TaskMemberDto(task, user, TaskUserRole.OWNER));
        innerTask.getSubtasks().add(innerInnerTask);

        // save task
        Long taskId = taskRequest.save(task);
        Assertions.assertNotNull(taskId, "New saved task id == null");
        System.out.println(task);


        // reload task from server, DDD
        TaskDto loadedTask = taskRequest.fetchAllById(taskId);
        System.out.println(loadedTask);
    }





    @Test
    @Order(3)
    void loadById() {
        log.info("loadById");

        // select user
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.USER);
        UserDto user = userRequest.getCurrent();

        // load task from server, DDD
        TaskDto loadedTask = taskRequest.fetchAllById(33L);
        System.out.println(loadedTask);
    }


    //log.info("result: {}", list);
}