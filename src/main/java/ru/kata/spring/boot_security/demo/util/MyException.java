package ru.kata.spring.boot_security.demo.util;

public class MyException {
    private String info;

    public MyException(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
