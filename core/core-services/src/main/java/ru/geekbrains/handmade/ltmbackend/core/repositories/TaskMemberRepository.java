package ru.geekbrains.handmade.ltmbackend.core.repositories;

import org.springframework.stereotype.Repository;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.Task;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.TaskMember;


@Repository
public interface TaskMemberRepository extends CustomRepository<TaskMember, Long> {}
