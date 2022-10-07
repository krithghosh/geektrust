package com.example.geektrust.model;

public class Fund {

    private int amount;
    private String fundType;

    public Fund(int amount, String fundType) {
        this.amount = amount;
        this.fundType = fundType;
    }

    public int getAmount() {
        return amount;
    }

    public String getFundType() {
        return fundType;
    }
}