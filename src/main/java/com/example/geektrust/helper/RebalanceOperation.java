package com.example.geektrust.helper;


import com.example.geektrust.common.Utils;
import com.example.geektrust.model.Allocate;
import com.example.geektrust.service.InitialInvestment;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class RebalanceOperation implements Operation {

    private InitialInvestment initialInvestment;

    public static RebalanceOperation rebalanceOperation;

    private RebalanceOperation() {
    }

    private RebalanceOperation(InitialInvestment initialInvestment) {
        this.initialInvestment = initialInvestment;
    }

    public static RebalanceOperation getInstance(InitialInvestment initialInvestment) {
        if (rebalanceOperation == null) {
            rebalanceOperation = new RebalanceOperation(initialInvestment);
        }
        return rebalanceOperation;
    }

    @Override
    public List<Integer> processRequest(TreeMap<Integer, LinkedList<Allocate>> transactions, LinkedList<String> input) throws Exception {
        int size = transactions.size();

        if (size < 6) {
            System.out.println("CANNOT_REBALANCE");
            return null;
        }

        if (size >= 12 && transactions.get(Utils.getMonth("DECEMBER")).size() == 3) {
            System.out.println(transactions.get(Utils.getMonth("DECEMBER")).getLast().toString());
            return null;
        }

        if (transactions.get(Utils.getMonth("JUNE")).size() == 3) {
            System.out.println(transactions.get(Utils.getMonth("JUNE")).getLast().toString());
            return null;
        }

        LinkedList<Integer> fundsAllocated = transactions.lastEntry().getValue().getLast().getFundsValue();
        int sum = fundsAllocated.stream().mapToInt(Integer::intValue).sum();
        return initialInvestment.getInitialAllocationPercent().stream().map(x -> (int) Math.floor(x * sum)).collect(Collectors.toList());
    }

    @Override
    public void processAllocation(TreeMap<Integer, LinkedList<Allocate>> transactions, String month, List<Integer> amount) {
        if (amount == null) return;

        LinkedList<Allocate> allocations = transactions.lastEntry().getValue();
        Allocate allocate = new Allocate(amount);
        allocations.add(allocate);
        transactions.put(Utils.getMonth(month), allocations);
        System.out.println(allocate.toString());
    }
}
