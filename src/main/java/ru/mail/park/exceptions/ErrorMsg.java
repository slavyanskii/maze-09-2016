package ru.mail.park.exceptions;

/**
 * Created by viacheslav on 03.10.16.
 */
public class ErrorMsg {
    private String error;
    private String msg;

    public ErrorMsg(String error, String msg) {
        this.error = error;
        this.msg = msg;
    }

    @SuppressWarnings("unused")
    public void setError(String error) {
        this.error = error;
    }
    @SuppressWarnings("unused")
    public void setMsg(String msg) {
        this.msg = msg;
    }
    @SuppressWarnings("unused")
    public String getError() {
        return error;
    }
    @SuppressWarnings("unused")
    public String getMsg() {
        return msg;
    }
}
