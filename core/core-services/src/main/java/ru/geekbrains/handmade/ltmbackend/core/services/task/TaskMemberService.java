package ru.geekbrains.handmade.ltmbackend.core.services.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.handmade.ltmbackend.core.entities.task.TaskMember;
import ru.geekbrains.handmade.ltmbackend.core.repositories.TaskMemberRepository;
import ru.geekbrains.handmade.ltmbackend.core.services.base.BaseRepoAccessService;

@Service
@Transactional
@Slf4j
public class TaskMemberService extends BaseRepoAccessService<TaskMember> {

    private final TaskMemberRepository repository;

    public TaskMemberService(TaskMemberRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
