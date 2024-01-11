package com.minproject.session.model;

public enum ResponseStatus {
    SUCCESS(0, "Okay"),
    PASSWORD_NOT_MATCH(100, "password not match"),

    USERNAME_EXIST(500, "user name exists"),
    USER_NONEXIST(600, "user not exist"),
    CREATE_SESSION_FAIL(700, "create session fail"),
    SESSION_NONEXIST(800, "session not exist"),
    SESSION_END(900, "session end"),
    RESTAURANT_EXIST(1000, "RESTAURANT exists");


    private final int code;

    private final String message;

    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
