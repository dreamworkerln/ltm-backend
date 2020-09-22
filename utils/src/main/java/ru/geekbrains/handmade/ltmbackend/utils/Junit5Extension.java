package ru.geekbrains.handmade.ltmbackend.utils;

import ru.geekbrains.handmade.ltmbackend.utils.testcontainers.PostgresContainerStarter;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

public class Junit5Extension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource, AfterAllCallback {

    private static boolean started = false;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {

        if (!started) {

            //System.out.println("================================================ " + "BEFORE ALL");

            PostgresContainerStarter.start();

            started = true;
            // Your "before all tests" startup logic goes here
            // The following line registers a callback hook when the root test context is shut down
            context.getRoot().getStore(GLOBAL).put("CMS_JUNIT_DOCKER_STARTER", this);
        }

    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        //System.out.println("================================================ " + "AFTER ALL");
    }



    @Override
    public void close() throws Throwable {
        //System.out.println("================================================ " + "CLOSE");
        PostgresContainerStarter.stop();
    }
}