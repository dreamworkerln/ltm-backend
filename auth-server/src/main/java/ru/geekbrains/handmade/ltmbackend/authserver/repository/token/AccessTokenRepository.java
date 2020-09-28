package ru.geekbrains.handmade.ltmbackend.authserver.repository.token;

import ru.geekbrains.handmade.ltmbackend.core.entities.oauth2.token.AccessToken;
import ru.geekbrains.handmade.ltmbackend.core.repositories.CustomRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccessTokenRepository extends CustomRepository<AccessToken, Long> {

    @Modifying
    @Query("DELETE FROM AccessToken t WHERE t.expiredAt < CURRENT_TIMESTAMP")
    void vacuum();
}
