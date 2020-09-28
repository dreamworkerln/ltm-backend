package ru.geekbrains.handmade.ltmbackend.authserver.repository;

import ru.geekbrains.handmade.ltmbackend.authserver.entities.BlacklistedToken;
import ru.geekbrains.handmade.ltmbackend.core.repositories.CustomRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlacklistedTokenRepository extends CustomRepository<BlacklistedToken, Long> {

    @Modifying
    @Query("DELETE FROM BlacklistedToken t WHERE t.expiredAt < CURRENT_TIMESTAMP")
    void vacuum();

    List<BlacklistedToken> findByIdGreaterThanEqual(@Param("from")Long from);
}
