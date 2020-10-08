package ru.geekbrains.handmade.ltmbackend.core.entities.task;

import lombok.*;
import ru.geekbrains.handmade.ltmbackend.core.entities.Address;
import ru.geekbrains.handmade.ltmbackend.core.entities.base.AbstractEntity;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.TaskUserRole;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.*;


/**
 * Сущность - задание - может стоять как на вершине
 * так быть и подзадачей
 *
 */
@Entity
@Data
@Table(name = "task")
//@NoArgsConstructor
//@AllArgsConstructor
public class Task extends AbstractEntity {


    // Родительское задание
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Task parent;

    // Список дочерних заданий
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<Task> tasks = new HashSet<>();

    //@Column(name = "address")

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="address_id")
    private Address address;


    @NotNull
    @Size(min = 3, max = 30)
    private String title;

    @OneToMany(mappedBy = "task", orphanRemoval = true, cascade = CascadeType.ALL)
    @MapKey(name = "user")
    private Map<User, TaskMember> members = new HashMap<>();
    //private Set<TaskMember> members = new HashSet<>();

//    // owner
//    @ManyToOne
//    @JoinColumn(name = "owner_id")
//    @NotNull
//    private User owner;

//    //executor, ответственный за исполнение
//    @ManyToOne
//    @JoinColumn(name = "executor_id")
//    @NotNull
//    private User executor;

//    // participans, include owner, executor and others teammates
//    @ManyToMany
//    @JoinTable(name = "member",
//        joinColumns = @JoinColumn(name = "task_id"),
//        inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private Set<User> members = new HashSet<>();

//    @ManyToMany(mappedBy = "task_member")
//    Set<User> users;

    @Column(name = "deadline")
    private Instant deadline;

    public Task() {}
    public Task(Task parent, String title, @NotNull User owner) {
        this.parent = parent;
        this.title = title;

        // add parent
        if(parent != null) {
            parent.getTasks().add(this);
        }

        // add owner
        addMember(owner, TaskUserRole.OWNER);
    }

    public void addMember(User user, TaskUserRole taskUserRole) {


        // user was already added to Task
        if(members.containsKey(user)) {
            throw new IllegalArgumentException("User " + user.getUsername() +
                " has been already assigned to task + " + title + ", " + id);
        }
        
        TaskMember taskMember = new TaskMember(this, user, taskUserRole);
        members.put(user, taskMember);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + id +
            ", title='" + title + '\'' +
            '}';
    }


}