package com.mtheron.anntp.example;

import com.mtheron.anntp.annotation.GetInsertStatement;

@GetInsertStatement
public class TestCase1 {

    private String field1;

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }
}