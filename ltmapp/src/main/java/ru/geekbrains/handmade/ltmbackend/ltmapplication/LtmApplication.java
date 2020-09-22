package ru.geekbrains.handmade.ltmbackend.ltmapplication;


import ru.geekbrains.handmade.ltmbackend.core.configurations.MultimoduleSpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MultimoduleSpringBootApplication
public class LtmApplication {
	public static void main(String[] args) {
		SpringApplication.run(LtmApplication.class, args);
	}
}