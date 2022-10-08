package com.example.geektrust.helper;


import com.example.geektrust.common.Utils;
import com.example.geektrust.exception.InvalidInput;
import com.example.geektrust.model.Allocate;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.example.geektrust.common.Constants.INVALID_INPUT_AMOUNTS;
import static com.example.geektrust.common.Constants.INVALID_INPUT_PARAMETER_COUNT;
import static com.example.geektrust.common.Constants.MONTH_JAUNARY;

public class InitialAllocateOperation implements Operation {

    private static InitialAllocateOperation initialAllocateOperation;

    private InitialAllocateOperation() {
    }

    public static InitialAllocateOperation getInstance() {
        if (initialAllocateOperation == null) {
            synchronized (InitialAllocateOperation.class) {
                if (initialAllocateOperation == null) {
                    initialAllocateOperation = new InitialAllocateOperation();
                }
            }
        }
        return initialAllocateOperation;
    }

    @Override
    public List<Integer> processRequest(TreeMap<Integer, LinkedList<Allocate>> transactions, LinkedList<String> input) throws Exception {
        if (input.size() != 4) {
            throw new InvalidInput(INVALID_INPUT_PARAMETER_COUNT, InitialAllocateOperation.class.getSimpleName(), "processRequest");
        }
        List<Integer> fundAllocation;
        try {
            fundAllocation = input.stream().skip(1).map(Integer::parseInt).collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new InvalidInput(INVALID_INPUT_AMOUNTS, InitialAllocateOperation.class.getSimpleName(), "processRequest");
        }
        initialAllocateOperation.processAllocation(transactions, MONTH_JAUNARY, fundAllocation);
        return fundAllocation;
    }

    @Override
    public void processAllocation(TreeMap<Integer, LinkedList<Allocate>> transactions, String month, List<Integer> amount) {
        LinkedList<Allocate> allocations = new LinkedList<>();
        Allocate allocate = new Allocate(amount);
        allocations.add(allocate);
        transactions.put(Utils.getMonth(month), allocations);
    }
}
