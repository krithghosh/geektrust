package com.example.geektrust.helper;


import com.example.geektrust.common.Utils;
import com.example.geektrust.model.Allocate;
import com.example.geektrust.model.Portfolio;
import com.example.geektrust.service.InitialInvestment;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.geektrust.common.Constants.MONTH_DECEMBER;
import static com.example.geektrust.common.Constants.MONTH_JUNE;
import static com.example.geektrust.common.Constants.OUTPUT_CANNOT_REBALANCE;

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
            synchronized (RebalanceOperation.class) {
                if (rebalanceOperation == null) {
                    rebalanceOperation = new RebalanceOperation(initialInvestment);
                }
            }
        }
        return rebalanceOperation;
    }

    @Override
    public List<Integer> processRequest(Portfolio portfolio, LinkedList<String> input) throws Exception {
        int size = portfolio.getTransactions().size();

        if (size < 6) {
            System.out.println(OUTPUT_CANNOT_REBALANCE);
            return null;
        }

        if (size >= 12 && portfolio.getTransactions().get(Utils.getMonth(MONTH_DECEMBER)).size() == 3) {
            System.out.println(portfolio.getTransactions().get(Utils.getMonth(MONTH_DECEMBER)).getLast().toString());
            return null;
        }

        if (portfolio.getTransactions().get(Utils.getMonth(MONTH_JUNE)).size() == 3) {
            System.out.println(portfolio.getTransactions().get(Utils.getMonth(MONTH_JUNE)).getLast().toString());
            return null;
        }

        LinkedList<Integer> fundsAllocated = portfolio.getTransactions().lastEntry().getValue().getLast().getFundsValue();
        int sum = fundsAllocated.stream().mapToInt(Integer::intValue).sum();
        return initialInvestment.getInitialAllocationPercent().stream().map(x -> (int) Math.floor(x * sum)).collect(Collectors.toList());
    }

    @Override
    public void processAllocation(Portfolio portfolio, String month, List<Integer> amount) {
        if (amount == null) return;

        LinkedList<Allocate> allocations = portfolio.getTransactions().lastEntry().getValue();
        Allocate allocate = new Allocate(amount);
        allocations.add(allocate);
        portfolio.getTransactions().put(Utils.getMonth(month), allocations);
        System.out.println(allocate.toString());
    }
}
