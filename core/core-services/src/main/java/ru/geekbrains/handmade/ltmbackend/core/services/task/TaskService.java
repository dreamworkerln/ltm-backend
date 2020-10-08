package ru.geekbrains.handmade.ltmbackend.core.services.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.Task;
import ru.geekbrains.handmade.ltmbackend.core.repositories.TaskRepository;
import ru.geekbrains.handmade.ltmbackend.core.services.base.BaseRepoAccessService;

@Service
@Transactional
@Slf4j
public class TaskService extends BaseRepoAccessService<Task> {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        super(taskRepository);
        this.taskRepository = taskRepository;
    }
}
