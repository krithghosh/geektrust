package com.example.geektrust.helper;


import com.example.geektrust.common.Utils;
import com.example.geektrust.exception.InvalidInput;
import com.example.geektrust.model.Allocate;
import com.example.geektrust.model.Portfolio;

import java.util.LinkedList;
import java.util.List;

import static com.example.geektrust.common.Constants.BALANCE_NOT_AVAILABLE;

public class BalanceOperation implements Operation {

    public static BalanceOperation balanceOperation;

    private BalanceOperation() {
    }

    public static BalanceOperation getInstance() {
        if (balanceOperation == null) {
            synchronized (BalanceOperation.class) {
                if (balanceOperation == null) {
                    balanceOperation = new BalanceOperation();
                }
            }
        }
        return balanceOperation;
    }

    @Override
    public List<Integer> processRequest(Portfolio portfolio, LinkedList<String> input) throws Exception {
        int month = Utils.getMonth(input.getLast());
        if (month >= portfolio.getTransactions().size()) {
            throw new InvalidInput(BALANCE_NOT_AVAILABLE, BalanceOperation.class.getSimpleName(), "processRequest");
        }
        Allocate allocate = portfolio.getTransactions().get(month).getLast();
        System.out.println(allocate.toString());
        return null;
    }

    @Override
    public void processAllocation(Portfolio portfolio, String month, List<Integer> amount) {

    }
}
