package ru.geekbrains.handmade.ltmbackend.core.repositories;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.geekbrains.handmade.ltmbackend.core.entities.task.Task;

import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CustomRepository<Task, Long> {

    @Query("FROM Task t " +
           "JOIN TaskMember tm " +
           "WHERE tm.user = :#{#user}")
    List<Task> findByUser(@Param("user") User user);
}
