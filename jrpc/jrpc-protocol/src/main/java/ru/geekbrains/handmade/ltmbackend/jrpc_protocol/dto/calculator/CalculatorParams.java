package ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.calculator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalculatorParams {
    private double a;
    private double b;
}
