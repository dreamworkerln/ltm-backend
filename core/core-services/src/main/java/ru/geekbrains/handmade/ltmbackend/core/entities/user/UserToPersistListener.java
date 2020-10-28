package ru.geekbrains.handmade.ltmbackend.core.entities.user;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Component
@Data
@Slf4j
public class UserToPersistListener {

// SETTER INJECTION WILL NOT WORK
//    private AccountService accountService;
//
//    public void setAccountService(AccountService accountService) {
//        this.accountService = accountService;
//    }


    // !! SPRING STATIC FIELD DEPENDENCY INJECTOR !!
    // static private AccountService accountService;
    // Injecting a Spring dependency into a JPA EntityListener
    // https://stackoverflow.com/questions/12155632/injecting-a-spring-dependency-into-a-jpa-entitylistener
//    @Autowired
//    public void init(AccountService accountService) {
//
//       UserToPersistListener.accountService = accountService;
//    }

//    @PrePersist
//    @PreUpdate
//    public void methodExecuteBeforeSave(User user) {

//        // Make sure that User contains persisted UserRoles
//        for (UserRole role : new HashSet<>(user.getRoles())) {
//
//            if (role.getId() == null) {
//                user.getRoles().remove(role);
//                user.getRoles().add(userRoleService.findByName(role.getName())
//                    .orElseThrow(() -> new IllegalArgumentException("UserRole " + role.getName() + "not found")));
//            }
//        }

//        // создадим для User аккаунт, если его у него нет
//        if (user.getAccount() == null) {
//            accountService.findByUser(user)
//                .ifPresent(account -> {
//                    throw new IllegalArgumentException("User " + user.getUsername() + " already have Account");
//                });
//
//            Account account = new Account();
//            user.setAccount(account);
//            account.setUser(user);
//            accountService.save(account);
//        }
//    }
}
