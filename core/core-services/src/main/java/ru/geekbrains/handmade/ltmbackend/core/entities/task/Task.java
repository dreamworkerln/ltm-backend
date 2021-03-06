package ru.geekbrains.handmade.ltmbackend.core.entities.task;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Cascade;
import org.springframework.stereotype.Component;
import ru.geekbrains.handmade.ltmbackend.core.entities.Address;
import ru.geekbrains.handmade.ltmbackend.core.entities.base.AbstractEntity;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.UserToPersistListener;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserRole;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Сущность - задание - может стоять как на вершине
 * так быть и подзадачей
 *
 */
@Entity
@Data
@Table(name = "task")


//@NamedEntityGraph(name = Task.PARENT_SUBTASKS_MEMBERS_GRAPH,
//    attributeNodes= {
//        @NamedAttributeNode("parent"),
//        @NamedAttributeNode("subtasks"),
//        @NamedAttributeNode("members")}
//)

@NamedEntityGraph(name = Task.SUBTASKS_MEMBERS_GRAPH,
    attributeNodes= {
        @NamedAttributeNode("subtasks"),
        @NamedAttributeNode("members")}
)

//@EntityListeners(Task.TaskPersistListener.class)  // доп действия над сущностью до сохранения/после загрузки из БД

//@NamedEntityGraph(
//    name = Task.PARENT_SUBTASKS_MEMBERS_GRAPH,
//    attributeNodes = {
//        @NamedAttributeNode("parent"),
//        @NamedAttributeNode(value="subtasks",subgraph="subtasks_graph"),
//        @NamedAttributeNode("members")
//    },
//    subgraphs = {
//        @NamedSubgraph(
//            name = "subtasks_graph",
//            attributeNodes = {
//                @NamedAttributeNode("parent")
//            }
//        )
//    }
//)

public class Task extends AbstractEntity {

    //public static final String PARENT_SUBTASKS_MEMBERS_GRAPH = "task.parent.subtask.members";
    public static final String SUBTASKS_MEMBERS_GRAPH = "task.subtask.members";


    @NotNull
    @Size(min = 3, max = 30)
    private String title;


    // Родительское задание
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Task parent;

    // Subtasks - список дочерних заданий
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<Task> subtasks = new HashSet<>();

    //@Column(name = "address")

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="address_id")
    private Address address;

    /*
    @OneToMany(mappedBy = "task", orphanRemoval = true, cascade = CascadeType.ALL)
    //@MapKey(name = "user")
    //@MapKeyJoinColumn(name = "user")
    //private Map<User, TaskMember> members = new HashMap<>();
    @Setter(AccessLevel.NONE) // инвалиды отакуе!
    private Set<TaskMember> membersPersist = new HashSet<>();

    @Transient
    private Map<User, TaskMember> members = new HashMap<>();

    */

    // Не надо пытаться пихать сюда одного пользователя больше одного раза с разными ролями,
    // Обладание одним пользователем несколькими ролями в контексте одной задачи не предусмотрено
    @OneToMany(mappedBy = "task", orphanRemoval = true, cascade = CascadeType.ALL)
    @MapKeyJoinColumn(name="user_id")
    private Map<User, TaskMember> members = new HashMap<>();

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
    public Task(String title, Task parent, @NotNull User owner) {
        this.parent = parent;
        this.title = title;

        // add parent
        if(parent != null) {
            parent.getSubtasks().add(this);
        }

        // add owner member
        addMember(owner, TaskUserRole.OWNER);
    }

//    public Task(String title, Task parent, Set<Task> subtasks, Set<TaskMember> members) {
//
//        this.parent = parent;
//        this.title = title;
//        this.subtasks = subtasks;
//        this.members = members;
//        //this.members = members.stream().collect(Collectors.toMap(TaskMember::getUser, tm -> tm));
//    }

    public void addMember(User user, TaskUserRole taskUserRole) {

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






//    @Component
//    @Data
//    @Slf4j
//    public static class TaskPersistListener {
//
//
//        @PrePersist
//        @PreUpdate
//        public void methodExecutedBeforeSave(Task task) {
//
//            task.membersPersist.clear();
//            task.membersPersist.addAll(task.members.values());
//        }
//
//
//        @PostLoad
//        public void methodExecutedAfterLoad(Task task) {
//
//            task.members = task.membersPersist.stream().collect(Collectors.toMap(
//                TaskMember::getUser,
//                Function.identity()));
//        }
//    }
}