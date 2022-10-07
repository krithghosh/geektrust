package com.example.geektrust.service;

import com.example.geektrust.model.Allocate;

import java.util.LinkedList;
import java.util.TreeMap;

public class BasePortfolio {
    public static final String ALLOCATE = "ALLOCATE";
    public static final String SIP = "SIP";
    public static final String CHANGE = "CHANGE";
    public static final String BALANCE = "BALANCE";
    public static final String REBALANCE = "REBALANCE";

    TreeMap<Integer, LinkedList<Allocate>> transactions;
    InitialInvestment initialInvestment;
    SystematicInvestment systematicInvestment;

    public BasePortfolio() {
        this.transactions = new TreeMap<>();
    }
}
