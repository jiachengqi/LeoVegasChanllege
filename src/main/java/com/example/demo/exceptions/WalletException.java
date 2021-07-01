package com.example.demo.exceptions;

public class WalletException extends Exception {
    private String msg;

    public WalletException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
