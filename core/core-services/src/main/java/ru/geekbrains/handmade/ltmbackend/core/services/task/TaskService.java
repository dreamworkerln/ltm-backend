package ru.geekbrains.handmade.ltmbackend.core.services.task;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.Task;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.repositories.TaskRepository;
import ru.geekbrains.handmade.ltmbackend.core.services.base.BaseRepoAccessService;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class TaskService extends BaseRepoAccessService<Task> {

    private final TaskRepository repository;
    private final UserService userService;

    @Autowired
    public TaskService(TaskRepository repository, UserService userService) {
        super(repository);
        this.repository = repository;
        this.userService = userService;
    }

//    /**
//     * Get tasks for current user
//     */
//    public List<Task> findByCurrentUser() {
//
//        User user = userService.getCurrent();
//        return findByUser(user);
//    }


    /**
     * Get task by user
     */
    public List<Task> findByUser(User user) {
        return
            repository.findByUser(user, EntityGraphs.named(Task.PARENT_SUBTASKS_MEMBERS_GRAPH));
    }

    @Override
    public Optional<Task> findById(Long id) {
        return repository.findById(id, EntityGraphs.named(Task.PARENT_SUBTASKS_MEMBERS_GRAPH));
    }

    public void truncateLazy(Task task) {
        repository.truncateLazy(task);
    }
}
