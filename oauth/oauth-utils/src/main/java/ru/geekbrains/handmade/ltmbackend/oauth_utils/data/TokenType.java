package ru.geekbrains.handmade.ltmbackend.oauth_utils.data;

import java.util.HashMap;
import java.util.Map;

public enum TokenType {



    ACCESS("access_token", 3600*24),       // сутки
    REFRESH("refresh_token", 3600*24*30),  // месяц
    CONFIRM("confirm_token", 3600*24);     // сутки      токен для подтверждения регистрации

    public static final String TOKEN_TYPE_NAME = "type";
    public static final String TOKEN_AUTHORITIES = "authorities";


    private static final Map<String, TokenType> values = new HashMap<>();

    static {
        for (TokenType t :TokenType.values()) {
            values.put(t.name, t);
        }
    }

    private final Long ttl;
    private final String name;


    TokenType(String name, long ttl) {
        this.ttl = ttl;
        this.name = name;
    }

    /**
     * In seconds
     */
    public long getTtl() {
        return ttl;
    }

    public String getName() {
        return name;
    }

    public static TokenType getByName(String name) {

        if (!values.containsKey(name)) {
            throw new IllegalArgumentException("Token by name " + name + "not found");
        }
        return values.get(name);
    }



}
