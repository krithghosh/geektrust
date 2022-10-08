package com.example.geektrust.helper;

import com.example.geektrust.common.Utils;
import com.example.geektrust.exception.InvalidInput;
import com.example.geektrust.model.Allocate;
import com.example.geektrust.model.Portfolio;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.geektrust.common.Constants.*;

public class SystematicInvestmentOperation implements Operation {
    private static SystematicInvestmentOperation systematicInvestmentOperation;

    private SystematicInvestmentOperation() {
    }

    public static SystematicInvestmentOperation getInstance() {
        if (systematicInvestmentOperation == null) {
            synchronized (SystematicInvestmentOperation.class) {
                if (systematicInvestmentOperation == null) {
                    systematicInvestmentOperation = new SystematicInvestmentOperation();
                }
            }
        }
        return systematicInvestmentOperation;
    }

    @Override
    public List<Integer> processRequest(Portfolio portfolio, LinkedList<String> input) throws Exception {
        if (input.size() != 4) {
            throw new InvalidInput(INVALID_INPUT_PARAMETER_COUNT, SystematicInvestmentOperation.class.getSimpleName(), "processRequest");
        }
        try {
            return input.stream().skip(1).map(Integer::parseInt).collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new InvalidInput(INVALID_INPUT_AMOUNTS, SystematicInvestmentOperation.class.getSimpleName(), "processRequest");
        }
    }

    @Override
    public void processAllocation(Portfolio portfolio, String month, List<Integer> amount) throws InvalidInput {
        if (Utils.getMonth(month) < 1) {
            throw new InvalidInput(INVALID_MONTH_FOR_SIP_START, SystematicInvestmentOperation.class.getSimpleName(), "processAllocation");
        }
        if (portfolio.getTransactions().size() == 0) {
            throw new InvalidInput(INVALID_INPUT_ORDER, SystematicInvestmentOperation.class.getSimpleName(), "processAllocation");
        }
        List<Integer> LastFundAllocated = portfolio.getTransactions().get(Utils.getPreviousMonth(month)).getLast().getFundsValue();
        List<Integer> sipAllocations = IntStream.range(0, LastFundAllocated.size())
                .mapToObj(i -> (LastFundAllocated.get(i) + amount.get(i))).collect(Collectors.toList());
        LinkedList<Allocate> allocations = new LinkedList<>();
        Allocate allocate = new Allocate(sipAllocations);
        allocations.add(allocate);
        portfolio.getTransactions().put(Utils.getMonth(month), allocations);
    }
}
