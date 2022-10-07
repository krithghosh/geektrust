package com.example.geektrust.helper;

import com.example.geektrust.common.Utils;
import com.example.geektrust.model.Allocate;
import com.example.geektrust.model.Fund;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChangeOperation implements Operation {

    private static ChangeOperation changeOperation;

    private ChangeOperation() {
    }

    public static ChangeOperation getInstance() {
        if (changeOperation == null) {
            changeOperation = new ChangeOperation();
        }
        return changeOperation;
    }

    @Override
    public List<Integer> processRequest(TreeMap<Integer, LinkedList<Allocate>> transactions, LinkedList<String> input) throws Exception {
        if (input.size() != 5) throw new Exception("INVALID INPUT");
        int month = Utils.getMonth(input.getLast());
        double[] percent = IntStream.range(0, input.size()).filter(i -> (i != 0 && i != 4))
                .mapToObj(x -> input.get(x).replace("%", "")).mapToDouble(x -> Double.parseDouble(x) / 100).toArray();
        List<Fund> existingAllocation = transactions.get(month).getLast().getFunds();
        return IntStream.range(0, percent.length).mapToObj(i -> (int) ((1 + percent[i]) * existingAllocation.get(i).getAmount()))
                .collect(Collectors.toList());
    }

    @Override
    public void processAllocation(TreeMap<Integer, LinkedList<Allocate>> transactions, String month, List<Integer> amount) {
        LinkedList<Allocate> allocations = transactions.get(Utils.getMonth(month));
        Allocate allocate = new Allocate(amount);
        allocations.add(allocate);
        transactions.put(Utils.getMonth(month), allocations);
    }
}