package com.example.geektrust.model;

import com.example.geektrust.common.Utils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Allocate {
    protected List<Fund> funds;
    protected int totalFundsAmount;

    public Allocate(List<Integer> amountAllocation) {
        totalFundsAmount = amountAllocation.stream().mapToInt(Integer::intValue).sum();
        funds = IntStream.range(0, amountAllocation.size())
                .mapToObj(k -> new Fund(amountAllocation.get(k), Utils.getFundType().get(k))).collect(Collectors.toList());
    }

    public List<Fund> getFunds() {
        return funds;
    }

    @Override
    public String toString() {
        return funds.get(0).getAmount() + " " + funds.get(1).getAmount() + " " + funds.get(2).getAmount();
    }

    public LinkedList<Integer> getFundsValue() {
        return new LinkedList<>(funds.stream().map(Fund::getAmount).collect(Collectors.toList()));
    }
}