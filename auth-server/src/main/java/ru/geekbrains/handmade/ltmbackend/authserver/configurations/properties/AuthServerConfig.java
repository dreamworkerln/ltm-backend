package ru.geekbrains.handmade.ltmbackend.authserver.configurations.properties;

import ru.geekbrains.handmade.ltmbackend.core.utils.EnvStringBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import ru.geekbrains.handmade.ltmbackend.authserver.data.constants.AuthServerPropNames;

import javax.annotation.PostConstruct;

import static ru.geekbrains.handmade.ltmbackend.core.data.constants.CorePropNames.*;
import static com.pivovarit.function.ThrowingSupplier.unchecked;

@Configuration
@Getter
public class AuthServerConfig {

    private final EnvStringBuilder envStringbuilder;

    @Value("${" + AuthServerPropNames.AUTHSERVER_CONFIRMATION_PATH + "}")
    @Getter(AccessLevel.NONE)
    private String confirmationPath;


    @Value("${" + AuthServerPropNames.AUTHSERVER_CONFIRMATION_REDIRECT_URL + "}")
    private String redirectUrl;


    private String confirmationUrl;



    @Autowired
    public AuthServerConfig(EnvStringBuilder envStringbuilder) {
        this.envStringbuilder = envStringbuilder;
    }

    @PostConstruct
    private void postConstruct() {
        confirmationUrl  =
            envStringbuilder.buildURL(envStringbuilder.getProperty(AuthServerPropNames.AUTHSERVER_CONFIRMATION_PATH));
    }
}
