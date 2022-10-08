package com.example.geektrust.helper;

import com.example.geektrust.common.Utils;
import com.example.geektrust.exception.InvalidInput;
import com.example.geektrust.model.Allocate;
import com.example.geektrust.model.Fund;
import com.example.geektrust.model.Portfolio;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.geektrust.common.Constants.INVALID_INPUT_AMOUNTS;
import static com.example.geektrust.common.Constants.INVALID_INPUT_PARAMETER_COUNT;

public class ChangeOperation implements Operation {

    private static ChangeOperation changeOperation;

    private ChangeOperation() {
    }

    public static ChangeOperation getInstance() {
        if (changeOperation == null) {
            synchronized (ChangeOperation.class) {
                if (changeOperation == null) {
                    changeOperation = new ChangeOperation();
                }
            }
        }
        return changeOperation;
    }

    @Override
    public List<Integer> processRequest(Portfolio portfolio, LinkedList<String> input) throws Exception {
        if (input.size() != 5) {
            throw new InvalidInput(INVALID_INPUT_PARAMETER_COUNT, ChangeOperation.class.getSimpleName(), "processRequest");
        }
        int month = Utils.getMonth(input.getLast());
        double[] percent;
        List<Fund> existingAllocation;
        try {
            percent = IntStream.range(0, input.size()).filter(i -> (i != 0 && i != 4))
                    .mapToObj(x -> input.get(x).replace("%", "")).mapToDouble(x -> Double.parseDouble(x) / 100).toArray();
            existingAllocation = portfolio.getTransactions().get(month).getLast().getFunds();
        } catch (NumberFormatException e) {
            throw new InvalidInput(INVALID_INPUT_AMOUNTS, ChangeOperation.class.getSimpleName(), "processRequest");
        }
        return IntStream.range(0, percent.length).mapToObj(i -> (int) ((1 + percent[i]) * existingAllocation.get(i).getAmount()))
                .collect(Collectors.toList());
    }

    @Override
    public void processAllocation(Portfolio portfolio, String month, List<Integer> amount) {
        LinkedList<Allocate> allocations = portfolio.getTransactions().get(Utils.getMonth(month));
        Allocate allocate = new Allocate(amount);
        allocations.add(allocate);
        portfolio.getTransactions().put(Utils.getMonth(month), allocations);
    }
}