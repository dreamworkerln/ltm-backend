package ru.geekbrains.handmade.ltmbackend.core.repositories;

import ru.geekbrains.handmade.ltmbackend.core.entities.Client;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CustomRepository<Client, Long> {

    Optional<Client> findOneByUserLastNameAndUserFirstName(String lastName, String firstName);
    Optional<Client> findOneByUser(User user);
    Optional<Client> findOneByUserUsername(String username);

}
