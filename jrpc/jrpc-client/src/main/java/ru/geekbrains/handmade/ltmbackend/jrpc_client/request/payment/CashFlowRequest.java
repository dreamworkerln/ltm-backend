package ru.geekbrains.handmade.ltmbackend.jrpc_client.request.payment;

import com.fasterxml.jackson.databind.JsonNode;
import ru.geekbrains.handmade.ltmbackend.jrpc_client.request.base.AbstractJrpcRequest;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.HandlerName;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.payment.CashFlowDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.payment.CashFlowSpecDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class CashFlowRequest extends AbstractJrpcRequest {

    @SneakyThrows
    public List<CashFlowDto> findAll(CashFlowSpecDto spec) {
        String uri = HandlerName.payment.cashflow.path + "." + HandlerName.payment.cashflow.findAll;
        JsonNode response = performJrpcRequest(uri, spec);
        return Arrays.asList(objectMapper.treeToValue(response, CashFlowDto[].class));
    }

    @SneakyThrows
    public Long save(CashFlowDto cashFlow) {
        String uri = HandlerName.payment.cashflow.path + "." +HandlerName.payment.cashflow.save;
        JsonNode response = performJrpcRequest(uri, cashFlow);
        return objectMapper.treeToValue(response, Long.class);
    }
}

