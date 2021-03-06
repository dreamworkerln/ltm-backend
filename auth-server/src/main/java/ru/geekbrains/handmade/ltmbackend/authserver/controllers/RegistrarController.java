package ru.geekbrains.handmade.ltmbackend.authserver.controllers;

import ru.geekbrains.handmade.ltmbackend.authserver.configurations.properties.AuthServerConfig;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.UnconfirmedUser;
import ru.geekbrains.handmade.ltmbackend.authserver.exceptions.UserAlreadyExistsException;
import ru.geekbrains.handmade.ltmbackend.authserver.service.RegistrarService;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.UserRole;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.net.URI;

@RestController
@RequestMapping("/registration/")
@Slf4j
public class RegistrarController {

    private final RegistrarService registrarService;
    private final AuthServerConfig authServerConfig;

    public RegistrarController( RegistrarService registrarService, AuthServerConfig authServerConfig) {
        this.registrarService = registrarService;
        this.authServerConfig = authServerConfig;
    }


    @PostMapping("/new")
    public ResponseEntity<?> add(@RequestBody UnconfirmedUser newUser) {

        ResponseEntity<?> result; // ResponseEntity.badRequest().body("Bad user");

        try {
            newUser.setRoles(UserRole.grantedAuthorityToRoles(UserService.getCurrentUserAuthorities()));

            String registrantToken = registrarService.registrate(newUser);
            // toDo: remove returning registrantToken after DEBUG
            result = ResponseEntity.ok(registrantToken);
        }
        catch (ConstraintViolationException e) {
            log.error("User validation error", e);
            String message = "User validation error: " + e.getConstraintViolations().toString();
            result = ResponseEntity.badRequest().body(message);
        }
        catch(UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            log.error("Adding new user error", e);
            result = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    @GetMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestParam("token") String token) {

        ResponseEntity<?> result; //= ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        try {
            registrarService.confirm(token);

            // отправляем пользователя на login page фронта
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(authServerConfig.getRedirectUrl()));
            result = new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
        catch (JwtException e) {
            result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        catch(UsernameNotFoundException e) {

            result = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Registration request not found / already registered ?");
        }
        catch (Exception e) {
            log.error("Confirm new user error", e);
            result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() + ": " +  e.getMessage());
        }
        return result;
    }


}
