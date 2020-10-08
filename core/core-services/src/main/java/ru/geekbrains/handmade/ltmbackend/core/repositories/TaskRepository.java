package ru.geekbrains.handmade.ltmbackend.core.repositories;


import org.springframework.stereotype.Repository;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.Task;

@Repository
public interface TaskRepository extends CustomRepository<Task, Long> {

}
