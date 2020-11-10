package ru.geekbrains.handmade.ltmbackend.core.services.task;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.Task;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.repositories.TaskRepository;
import ru.geekbrains.handmade.ltmbackend.core.services.base.BaseRepoAccessService;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserRole;

import java.util.*;

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


    public List<Task> findByUser(User user) {
        return repository.findByUser(user, EntityGraphs.named(Task.SUBTASKS_MEMBERS_GRAPH));
    }

    @Override
    public Optional<Task> findById(Long id) {
        return super.findById(id, EntityGraphs.named(Task.SUBTASKS_MEMBERS_GRAPH));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<Task> fetchAllById(Long id) {
        Optional<Task> result = super.findById(id, EntityGraphs.named(Task.SUBTASKS_MEMBERS_GRAPH));

        // Breadth First Search
        if (result.isPresent()) {
            Task task = result.get();
            Deque <Task> deck = new LinkedList<>();
            deck.add(task);

            while (deck.size() != 0) {
                Task sub = deck.poll();
                deck.addAll(sub.getSubtasks()); // will perform query to get all subtasks
                sub.getMembers().size();        // will perform query to get all members
            }
        }

        return result;
    }

//    public Optional<Task> findByIdZeta(Long id) {
//        return repository.findByIdZeta(id, EntityGraphs.named(Task.PARENT_SUBTASKS_MEMBERS_GRAPH));
//    }

        @Override
        public Task findByIdOrError(Long id) {
        return super.findById(id, EntityGraphs.named(Task.SUBTASKS_MEMBERS_GRAPH))
            .orElseThrow(() -> new IllegalArgumentException("Entity by id: " + id + " not found"));
    }


        // Подразумевается, что в конкретном задании у одного пользователя одновременно может существовать только одна роль
        public Optional<TaskUserRole> getTaskMemberRole(Long taskId, User user) {
        return
            repository.getTaskMemberRole(taskId, user, EntityGraphs.empty());
    }

        // ----------------------------------------------------------------------

        /**
         * Replace not initialized lazy JPA Proxies to null
         * @param task task to truncate Lazy proxies in
         */
        public void truncateLazy(Task task) {
        repository.truncateLazy(task);
    }
    }
