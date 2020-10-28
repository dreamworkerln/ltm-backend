package ru.geekbrains.handmade.ltmbackend.core.repositories;

import ru.geekbrains.handmade.ltmbackend.core.entities.Order;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface OrderRepository extends CustomRepository<Order, Long> {

    // SELECT FOR UPDATE
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("FROM Order o " +
           "WHERE o = :#{#order}")
    List<Order> lockByOrder(@Param("order")Order order);
}
