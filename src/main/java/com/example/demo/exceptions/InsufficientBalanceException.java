package com.example.demo.exceptions;

public class InsufficientBalanceException extends Exception {
    private String msg;

    public InsufficientBalanceException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
