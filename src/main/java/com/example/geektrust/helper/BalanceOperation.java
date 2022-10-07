package com.example.geektrust.helper;


import com.example.geektrust.common.Utils;
import com.example.geektrust.exception.InvalidInput;
import com.example.geektrust.model.Allocate;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import static com.example.geektrust.common.Constants.BALANCE_NOT_AVAILABLE;

public class BalanceOperation implements Operation {

    public static BalanceOperation balanceOperation;

    private BalanceOperation() {
    }

    public static BalanceOperation getInstance() {
        if (balanceOperation == null) {
            balanceOperation = new BalanceOperation();
        }
        return balanceOperation;
    }

    @Override
    public List<Integer> processRequest(TreeMap<Integer, LinkedList<Allocate>> transactions, LinkedList<String> input) throws Exception {
        int month = Utils.getMonth(input.getLast());
        if (month >= transactions.size()) {
            throw new InvalidInput(BALANCE_NOT_AVAILABLE, BalanceOperation.class.getSimpleName(), "processRequest");
        }
        Allocate allocate = transactions.get(month).getLast();
        System.out.println(allocate.toString());
        return null;
    }

    @Override
    public void processAllocation(TreeMap<Integer, LinkedList<Allocate>> transactions, String month, List<Integer> amount) {
        
    }
}
