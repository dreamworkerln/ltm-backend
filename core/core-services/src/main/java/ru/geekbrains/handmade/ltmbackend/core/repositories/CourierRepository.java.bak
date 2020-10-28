package ru.geekbrains.handmade.ltmbackend.core.repositories;

import ru.geekbrains.handmade.ltmbackend.core.entities.Client;
import ru.geekbrains.handmade.ltmbackend.core.entities.Courier;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierRepository extends CustomRepository<Courier, Long> {

    Optional<Courier> findOneByUserLastNameAndUserFirstName(String lastName, String firstName);
    Optional<Courier> findOneByUser(User user);
    Optional<Courier> findOneByUserUsername(String username);
}
