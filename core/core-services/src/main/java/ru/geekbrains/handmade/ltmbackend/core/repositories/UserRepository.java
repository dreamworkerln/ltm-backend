package ru.geekbrains.handmade.ltmbackend.core.repositories;

import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CustomRepository<User, Long> {

    Optional<User> findOneByUsername(String username);
    Optional<User> findOneByLastNameAndFirstName(String lastName, String firstName);

    //@Query("select case when count(c)> 0 then true else false end from Car c where lower(c.model) like lower(:model)")

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
        "FROM User u " +
        "WHERE " +
        "lower(u.username) = :#{#user.username.toLowerCase()} OR " +
        "lower(u.lastName) = :#{#user.lastName.toLowerCase()} AND u.firstName = :#{#user.firstName} OR " +
        "lower(u.email) = :#{#user.email.toLowerCase()} OR " +
        "u.phoneNumber = :#{#user.phoneNumber}")
    boolean checkIfExists(@Param("user")User user);


    Optional<User> findOneByEmail(String email);
    Optional<User> findOneByPhoneNumber(String phoneNumber);



    //@EntityGraph(value = "Item.characteristics")
    //load

}
