package ru.geekbrains.handmade.ltmbackend.authserver.service;

import ru.geekbrains.handmade.ltmbackend.authserver.configurations.AuthServerSpringConfiguration;
import ru.geekbrains.handmade.ltmbackend.authserver.configurations.properties.AuthServerConfig;
import ru.geekbrains.handmade.ltmbackend.core.configurations.CoreSpringConfiguration;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.UnconfirmedUser;
import ru.geekbrains.handmade.ltmbackend.authserver.exceptions.UserAlreadyExistsException;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.mail.services.MailService;
import ru.geekbrains.handmade.ltmbackend.oauth_utils.data.TokenType;
import ru.geekbrains.handmade.ltmbackend.oauth_utils.services.JwtTokenService;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.UserRole;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

import static com.pivovarit.function.ThrowingSupplier.unchecked;

@Service
@Transactional
@Slf4j
public class RegistrarService {

    private final AuthServerSpringConfiguration authServerSpringConfiguration;
    private final AuthServerConfig authServerConfig;
    private final UserService userService;
    private final UnconfirmedUserService unconfirmedUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final MailService mailService;
    private final Validator validator;

    public RegistrarService(AuthServerSpringConfiguration authServerSpringConfiguration, AuthServerConfig authServerConfig, UserService userService,
                            UnconfirmedUserService unconfirmedUserService,
                            PasswordEncoder passwordEncoder,
                            JwtTokenService jwtTokenService,
                            MailService mailService, Validator validator) {
        this.authServerSpringConfiguration = authServerSpringConfiguration;
        this.authServerConfig = authServerConfig;
        this.userService = userService;
        this.unconfirmedUserService = unconfirmedUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.mailService = mailService;
        this.validator = validator;
    }

    public String registrate(UnconfirmedUser newUser) {

        Set<ConstraintViolation<UnconfirmedUser>> violations = validator.validate(newUser);
        if (violations.size() != 0) {
            throw new ConstraintViolationException("User validation failed", violations);
        }

        // user already exists in User or in UnconfirmedUser
        if (userService.checkIfExists(newUser.toUser()) ||
            unconfirmedUserService.checkIfExists(newUser)) {
            throw new UserAlreadyExistsException(newUser.getUsername());
        }

        log.info("Adding new user: {}", newUser);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        newUser.getRoles().clear();
        newUser.getRoles().add(UserRole.CONFIRM_REGISTRATION);

        // save new user to UnconfirmedUser
        unconfirmedUserService.save(newUser);

        // create confirmation JWT
        Set<String> confirmationRole = newUser.getRoles().stream().map(UserRole::getName).collect(Collectors.toSet());
        String registrantToken = jwtTokenService.createJWT(
            TokenType.CONFIRM,
            newUser.getUsername(),
            authServerSpringConfiguration.getISSUER(),
            newUser.getUsername(),
            confirmationRole);

        URI url = unchecked(() -> new URI(authServerConfig.getConfirmationUrl() + "?token=" + registrantToken)).get();

        mailService.sendRegistrationConfirmation(newUser, url);

        return registrantToken;
    }


    public void confirm(String token) {

        // will throw exception if token not valid
        Claims claims = jwtTokenService.decodeJWT(token);

        String username = claims.getSubject();
        UnconfirmedUser unconfirmedUser = unconfirmedUserService.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));



        User user = unconfirmedUser.toUser();

        // Set user roles to USER
        user.getRoles().clear();
        user.getRoles().add(UserRole.USER);
        // save user
        userService.save(user);

        // remove unconfirmedUser
        unconfirmedUserService.delete(unconfirmedUser);
    }
}



//Set<String> confirmationRoles = new HashSet<>(Collections.singletonList(UserRole.CONFIRM_REGISTRATION));
