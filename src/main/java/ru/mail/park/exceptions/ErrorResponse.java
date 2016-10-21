package ru.mail.park.exceptions;

public class ErrorResponse {

    public static final String VALIDATION_ERROR = "Invalid input";
    public static final String USER_ALREADY_EXISTS = "User already exists";
    public static final String SERVER_ERROR = "Server error";
    public static final String AUTHORIZATION_ERROR = "Wrong login or password";

    private String msg;
    private String error;

    public ErrorResponse(String error, String msg) {
        this.msg = msg;
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
