package ru.geekbrains.handmade.ltmbackend.core.controllers.jrpc.http_wrapper;

import com.fasterxml.jackson.databind.JsonNode;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.protocol.JrpcBase;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.protocol.JrpcException;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.protocol.response.JrpcErrorCode;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.protocol.response.JrpcErrorResponse;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.protocol.response.JrpcResponse;
import org.springframework.http.HttpStatus;


// Создаватель HTTP ответов, инкапсулирует jrpc внутрь http
public class HttpResponseFactory {

    static public HttpResponse getOk(JrpcBase jrpcResponse) {
        return new HttpResponse(HttpStatus.OK, jrpcResponse);
    }

    static public HttpResponse getOk(JsonNode json) {
        return new HttpResponse(HttpStatus.OK, new JrpcResponse(json));
    }

    static public HttpResponse getUnauthorized() {

        JrpcErrorResponse error = new JrpcErrorResponse("Unauthorized", JrpcErrorCode.UNAUTHORIZED);
        return new HttpResponse(HttpStatus.UNAUTHORIZED, error);
    }

    static public HttpResponse getForbidden() {

        JrpcErrorResponse error = new JrpcErrorResponse("Unauthorized", JrpcErrorCode.FORBIDDEN);
        return new HttpResponse(HttpStatus.FORBIDDEN, error);
    }


    static public HttpResponse getError(JrpcException e) {

        // Include inner exception message
        String message = e.getMessage();
        if (e.getCause()!= null) {
            message = message + ": " + e.getCause().getMessage();
        }
        JrpcErrorResponse jrpcResult = new JrpcErrorResponse(message, e.getCode(), e.getData());
        return new HttpResponse(HttpStatus.BAD_REQUEST, jrpcResult);
    }


    static public HttpResponse getError(HttpStatus status, Throwable e) {

        String message = status.getReasonPhrase() + " " + e.getMessage();
        JrpcErrorCode jrpcErrorCode = status == HttpStatus.INTERNAL_SERVER_ERROR ?
            JrpcErrorCode.INTERNAL_SERVER_ERROR : JrpcErrorCode.INVALID_REQUEST;

        JrpcErrorResponse jrpcResult = new JrpcErrorResponse(message, jrpcErrorCode);
        return new HttpResponse(status, jrpcResult);
    }

}
