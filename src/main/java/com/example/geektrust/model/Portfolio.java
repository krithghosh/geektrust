package com.example.geektrust.model;

import com.example.geektrust.service.InitialInvestment;
import com.example.geektrust.service.SystematicInvestment;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

public class Portfolio {

    UUID id;
    TreeMap<Integer, LinkedList<Allocate>> transactions;
    InitialInvestment initialInvestment;
    SystematicInvestment systematicInvestment;

    public Portfolio() {
        this.id = UUID.randomUUID();
        transactions = new TreeMap<>();
    }

    public TreeMap<Integer, LinkedList<Allocate>> getTransactions() {
        return transactions;
    }

    public InitialInvestment getInitialInvestment() {
        return initialInvestment;
    }

    public SystematicInvestment getSystematicInvestment() {
        return systematicInvestment;
    }

    public void setInitialInvestment(List<Integer> fundAllocation) {
        this.initialInvestment = new InitialInvestment(fundAllocation);
    }

    public void setSystematicInvestment(List<Integer> fundAllocation) {
        this.systematicInvestment = new SystematicInvestment(fundAllocation);
    }

    public UUID getId() {
        return id;
    }
}
