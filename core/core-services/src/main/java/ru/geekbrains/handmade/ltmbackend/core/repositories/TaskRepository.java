package ru.geekbrains.handmade.ltmbackend.core.repositories;



import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
//import org.springframework.data.jpa.repository.Query;
import org.hibernate.collection.internal.PersistentMap;
import org.hibernate.collection.internal.PersistentSet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.geekbrains.handmade.ltmbackend.core.entities.task.Task;

import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserRole;

import javax.persistence.EntityManager;
import javax.persistence.NamedEntityGraph;
import javax.persistence.PersistenceUnitUtil;
import java.util.*;

@Repository
public interface TaskRepository extends CustomRepository<Task, Long> {

    //@EntityGraph(Task.PARENT_SUBTASKS_MEMBERS_GRAPH)
    @Query("SELECT DISTINCT t FROM Task t " +
        "JOIN TaskMember tm ON tm.task = t " +
        //"JOIN FETCH t.members " +
        //"LEFT JOIN FETCH t.parent " +
        //"LEFT JOIN FETCH t.subtasks " +
        "WHERE tm.user = :#{#user}")
    List<Task> findByUser(@Param("user") User user, EntityGraph entityGraph);



    // When explicitly define @Query Cosium/Hibernate will JOIN FETCH for self referencing entities(tables)
    // (Without will do for different tables but not for self referencing)
    @Query("FROM Task t " +
        "WHERE t.id = :#{#id}")
    Optional<Task> findById(@Param("id") Long id, EntityGraph entityGraph);


    // ----------------------------------------------------------------

    // Check if Task contains User
    @Query("SELECT tm.taskUserRole FROM Task t " +
        "JOIN TaskMember tm ON tm.task = t " +
        //"JOIN FETCH t.members " +
        //"LEFT JOIN FETCH t.parent " +
        //"LEFT JOIN FETCH t.subtasks " +
        "WHERE tm.user = :#{#user} AND t.id = :#{#taskId}")
    Optional<TaskUserRole> getTaskMemberRole(@Param("taskId")Long taskId, @Param("user")User user, EntityGraph entityGraph);




    // =================================================================

    // replace non-initialized AOP Hibernate proxy to empty Set/Map
    // for Task.subtasks and Task.members
    default void truncateLazy(Task task) {

        if(task != null) {
            PersistenceUnitUtil unitUtil = getPersistenceUnitUtil();
            for (Task sub : task.getSubtasks()) {

                if(!unitUtil.isLoaded(sub.getSubtasks())) {
                    sub.setSubtasks(new HashSet<>());
                }
//                if(!unitUtil.isLoaded(sub.getMembers())) {
//                    sub.setMembers(new HashMap<>());
//                }
                if(!unitUtil.isLoaded(sub.getMembers())) {
                    sub.setMembers(new HashSet<>());
                }

//                if (sub.getSubtasks().getClass() == PersistentSet.class) {
//                    sub.setSubtasks(new HashSet<>());
//                }
////                if (sub.getMembers().getClass() == PersistentSet.class) {
////                    sub.setMembers(new HashSet<>());
////                }
//                if (sub.getMembers().getClass() == PersistentMap.class) {
//                    sub.setMembers(new HashMap<>());
//                }

            }
        }
    }


//    public void lazyLoadFixSubtasks(Collection<Task> tasks) {
//
//        if(tasks != null) {
//            tasks.forEach(this::lazyLoadFixSubtasks);
//        }
//    }




    //    @Override
//    Optional<Task> findById(Long id, EntityGraph entityGraph);

//    // When define @Query Cosium will JOIN FETCH self referencing entities(tables)
//    // And here for example it will fetch subtasks
//    @Override
//    @Query("FROM Task t " +
//        "WHERE t.id = :#{#id}")
//    Optional<Task> findById(Long id, EntityGraph entityGraph);
}
