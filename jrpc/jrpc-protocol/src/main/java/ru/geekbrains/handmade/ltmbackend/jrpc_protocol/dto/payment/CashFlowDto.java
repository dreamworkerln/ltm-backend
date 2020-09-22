package ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.payment;

import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.AbstractDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.client.ClientDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.user.UserDto;
import ru.geekbrains.handmade.ltmbackend.utils.data.enums.CurrencyCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CashFlowDto extends AbstractDto {

    private UserDto user;
    private BigDecimal amount;
    private String payPalEmail;

    private CurrencyCode currencyCode;
    private String typeOperation;

    private Instant dateSuccess;

    public CashFlowDto(UserDto user, BigDecimal amount, String payPalEmail, CurrencyCode currencyCode) {
        this.user = user;
        this.amount = amount;
        this.payPalEmail = payPalEmail;
        this.currencyCode = currencyCode;
        this.typeOperation = "+залупа";
    }
}
