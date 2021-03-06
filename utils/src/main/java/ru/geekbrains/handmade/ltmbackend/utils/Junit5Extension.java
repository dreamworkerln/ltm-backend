package ru.geekbrains.handmade.ltmbackend.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ru.geekbrains.handmade.ltmbackend.utils.testcontainers.PostgresContainerStarter;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Properties;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

// https://stackoverflow.com/questions/43282798/in-junit-5-how-to-run-code-before-all-tests/51556718#51556718
// In JUnit 5, how to run code before all tests
public class Junit5Extension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource, AfterAllCallback {

    private static boolean started = false;
    
    // аннотации для Spring тут работать не будут, maven спринг еще не стартанул, контекст не поднялся
    // Этот класс вообще не managed'ся Spring
    //@Value("${testcontainers.enable:true}")
    private boolean testContainersEnabled = true;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {

        if(!testContainersEnabled) return;

        if (!started) {

            //System.out.println("================================================ " + "BEFORE ALL");

            PostgresContainerStarter.start();

            started = true;
            // Your "before all tests" startup logic goes here
            // The following line registers a callback hook when the root test context is shut down
            context.getRoot().getStore(GLOBAL).put("LTM_JUNIT_DOCKER_STARTER", this);
        }

    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        if(!testContainersEnabled) return;

        //System.out.println("================================================ " + "AFTER ALL");
    }



    @Override
    public void close() throws Throwable {
        if(!testContainersEnabled) return;

        //System.out.println("================================================ " + "CLOSE");
        PostgresContainerStarter.stop();
    }
}
