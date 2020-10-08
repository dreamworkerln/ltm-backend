package ru.geekbrains.handmade.ltmbackend.core.entities.task;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.TaskUserRole;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "task_member")
@Data
public class TaskMember {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private TaskUserRole taskUserRole;

    protected TaskMember() {}

    public TaskMember(Task task, User user, TaskUserRole taskUserRole) {

        this.task = task;
        this.user = user;
        this.taskUserRole = taskUserRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskMember)) return false;
        TaskMember that = (TaskMember) o;
        return user.getUsername().equals(that.user.getUsername()) &&
            task.equals(that.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, task);
    }

    @Override
    public String toString() {
        return "TaskMember{" +
            "id=" + id +
            ", user=" + user.getId() + ": " + user.getUsername() +
            ", task=" + task +
            ", taskUserRole=" + taskUserRole +
            '}';
    }
}
