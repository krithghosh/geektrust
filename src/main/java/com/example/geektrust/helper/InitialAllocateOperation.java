package com.example.geektrust.helper;


import com.example.geektrust.common.Utils;
import com.example.geektrust.exception.InvalidInput;
import com.example.geektrust.model.Allocate;
import com.example.geektrust.model.Portfolio;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.geektrust.common.Constants.INVALID_INPUT_AMOUNTS;
import static com.example.geektrust.common.Constants.INVALID_INPUT_PARAMETER_COUNT;
import static com.example.geektrust.common.Constants.MONTH_JANUARY;

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
    public List<Integer> processRequest(Portfolio portfolio, LinkedList<String> input) throws Exception {
        if (input.size() != 4) {
            throw new InvalidInput(INVALID_INPUT_PARAMETER_COUNT, InitialAllocateOperation.class.getSimpleName(), "processRequest");
        }
        List<Integer> fundAllocation;
        try {
            fundAllocation = input.stream().skip(1).map(Integer::parseInt).collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new InvalidInput(INVALID_INPUT_AMOUNTS, InitialAllocateOperation.class.getSimpleName(), "processRequest");
        }
        initialAllocateOperation.processAllocation(portfolio, MONTH_JANUARY, fundAllocation);
        return fundAllocation;
    }

    @Override
    public void processAllocation(Portfolio portfolio, String month, List<Integer> amount) {
        LinkedList<Allocate> allocations = new LinkedList<>();
        Allocate allocate = new Allocate(amount);
        allocations.add(allocate);
        portfolio.getTransactions().put(Utils.getMonth(month), allocations);
    }
}
