package ru.geekbrains.handmade.ltmbackend.ltmapplication.services;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public Double add(double a, double b) {
        return a + b;
    }

    public Double sub(double a, double b) {
        return a - b;
    }

    public Double mul(double a, double b) {
        return a * b;
    }

    public Double div(double a, double b) {
        return a / b;
    }
}
