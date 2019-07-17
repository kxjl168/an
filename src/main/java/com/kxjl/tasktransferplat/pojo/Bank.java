package com.kxjl.tasktransferplat.pojo;

import lombok.Data;

@Data
public class Bank {
    private String k;

    private String v;

    public Bank(String k, String v) {
        this.k = k;
        this.v = v;
    }
}
