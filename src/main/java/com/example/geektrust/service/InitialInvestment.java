package com.example.geektrust.service;

import com.example.geektrust.model.Allocate;

import java.util.LinkedList;
import java.util.List;

public class InitialInvestment extends Allocate {
    private List<Double> initialAllocationPercent = new LinkedList<>();

    public InitialInvestment(List<Integer> fundAllocation) {
        super(fundAllocation);

        for (Integer amount : fundAllocation) {
            initialAllocationPercent.add((double) amount / totalFundsAmount);
        }
    }

    public List<Double> getInitialAllocationPercent() {
        return initialAllocationPercent;
    }
}