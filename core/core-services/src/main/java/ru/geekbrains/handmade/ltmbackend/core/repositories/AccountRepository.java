package ru.geekbrains.handmade.ltmbackend.core.repositories;

import ru.geekbrains.handmade.ltmbackend.core.entities.Account;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface AccountRepository extends CustomRepository<Account, Long> {

    // SELECT FOR UPDATE
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("FROM Account a " +
           "WHERE a = :#{#account}")
    Optional<Account> lockByAccount(@Param("account")Account account);

    Optional<Account> findByUser(User user);
}
