package ru.geekbrains.handmade.ltmbackend.core.configurations;

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;
import ru.geekbrains.handmade.ltmbackend.core.repositories.RepositoryWithEntityManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited

@SpringBootApplication(scanBasePackages = "ru.geekbrains.handmade.ltmbackend")
@EnableJpaRepositories(basePackages = "ru.geekbrains.handmade.ltmbackend",
                       repositoryBaseClass = RepositoryWithEntityManager.class,
                       repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class)
@EntityScan(basePackages = {"ru.geekbrains.handmade.ltmbackend"})
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true)
public @interface MultimoduleSpringBootApplication {
}
