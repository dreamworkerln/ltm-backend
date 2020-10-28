package ru.geekbrains.handmade.ltmbackend.core.services.user;


import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphs;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.geekbrains.handmade.ltmbackend.core.entities.base.UserDetailsCustom;
import ru.geekbrains.handmade.ltmbackend.core.repositories.UserRepository;
import ru.geekbrains.handmade.ltmbackend.core.services.base.BaseRepoAccessService;
import ru.geekbrains.handmade.ltmbackend.utils.StringUtils;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;


@Service
@Transactional
public class UserService extends BaseRepoAccessService<User> {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        Optional<User> result = Optional.empty();
        if (!StringUtils.isBlank(username)) {
            result = userRepository.findOneByUsername(username);
        }
        return result;
    }


    /**
     * Find User by LastNameFirstName
     * @param lastName
     * @param firstName
     * @return
     */
    public Optional<User> findByFullName(String lastName, String firstName) {
        Optional<User> result = Optional.empty();
        if (!StringUtils.isBlank(lastName) && !StringUtils.isBlank(firstName)) {
            result = userRepository.findOneByLastNameAndFirstName(lastName, firstName);
        }
        return result;
    }


    public Optional<User> findByEmail(String email) {
        Optional<User> result = Optional.empty();
        if (!StringUtils.isBlank(email)) {
            result = userRepository.findOneByEmail(email);
        }
        return result;
    }

    public Optional<User> findByPhone(String phone) {
        Optional<User> result = Optional.empty();
        if (!StringUtils.isBlank(phone)) {
            result = userRepository.findOneByPhoneNumber(phone);
        }
        return result;
    }



    /**
     * Check if user already exists by username OR FullName OR email OR phoneNumber
     * @param user
     * @return
     */
    public boolean checkIfExists(User user) {
        return userRepository.checkIfExists(user);
    }


//    /**
//     * Выдает User вместе с refresh tokens? а зачем это нужно?
//     */
//    @Override
//    public Optional<User> findByIdEager(Long id) {
//        return userRepository.findById(id, EntityGraphs.named(User.FULL_ENTITY_GRAPH));
//    }






    /**
     * Get current authenticated user
     * @return User
     */
    public User getCurrent() {

        User result;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Auth server
        if (authentication.getPrincipal() instanceof UserDetailsCustom) {
            UserDetailsCustom userDetails = (UserDetailsCustom)authentication.getPrincipal();
            return userDetails.getUser();
        }
        // Resource server - in this configuration have access to user DB
        else {
            result = findByUsername(authentication.getName()).orElseThrow(() ->
            new UsernameNotFoundException("User " + authentication.getName() + " not found"));

        }
        return result;
    }




    /**
     * Get current authenticated user userName
     * @return User
     */
    public static String getCurrentUsername() {

        String result;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            result = userDetails.getUsername();
        }
        else {
            result = authentication.getName();
        }
        return result;
    }

    public static Collection<? extends GrantedAuthority> getCurrentUserAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
}
