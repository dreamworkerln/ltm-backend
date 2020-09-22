package ru.geekbrains.handmade.ltmbackend.ltmapplication.services;

import ru.geekbrains.handmade.ltmbackend.utils.Junit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith({Junit5Extension.class})
class CalculatorServiceTest {

    @Test
    void add() {
        System.out.println("213");
    }
}