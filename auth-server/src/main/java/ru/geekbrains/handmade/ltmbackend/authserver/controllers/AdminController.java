package ru.geekbrains.handmade.ltmbackend.authserver.controllers;

import ru.geekbrains.handmade.ltmbackend.authserver.configurations.AuthType;
import ru.geekbrains.handmade.ltmbackend.authserver.configurations.aop.ValidAuthenticationType;
import ru.geekbrains.handmade.ltmbackend.authserver.service.TokenService;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/")
@Slf4j
public class AdminController {

    private final TokenService tokenService;
    private final UserService userService;

    @Autowired
    public AdminController(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/test")
    @ValidAuthenticationType({AuthType.BASIC_AUTH, AuthType.ACCESS_TOKEN})
    @Secured(UserRole.VAL.ADMIN)
	public ResponseEntity<String> test() {
        return  ResponseEntity.ok("hello world");
	}


    @PostMapping("/user/revoke_token")
    @ValidAuthenticationType({AuthType.BASIC_AUTH, AuthType.ACCESS_TOKEN})
    @Secured(UserRole.VAL.ADMIN)
    public ResponseEntity<String> revokeTokenByUsername(@RequestBody String username) {

        HttpStatus result;
        User user = userService.findByUsername(username).orElse(null);
        
        if (user == null) {
            result = HttpStatus.NOT_FOUND;
        }
        else {
            tokenService.revokeRefreshToken(user.getRefreshTokenList());
            result = HttpStatus.OK;
        }
        return new ResponseEntity<>(result);
    }

}
