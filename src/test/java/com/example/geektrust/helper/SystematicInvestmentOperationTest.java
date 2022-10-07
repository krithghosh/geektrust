package com.example.geektrust.helper;

import com.example.geektrust.exception.InvalidInput;
import com.example.geektrust.model.Allocate;
import edu.emory.mathcs.backport.java.util.Arrays;
import junit.framework.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import static com.example.geektrust.common.Constants.INVALID_INPUT_AMOUNTS;
import static com.example.geektrust.common.Constants.INVALID_INPUT_PARAMETER_COUNT;
import static org.junit.jupiter.api.Assertions.*;

class SystematicInvestmentOperationTest {

    @Test
    void testInvalidRequestParameterCount() {
        TreeMap<Integer, java.util.LinkedList<Allocate>> transactions = new TreeMap<>();
        LinkedList<String> input = new LinkedList<String>(Arrays.asList(new String[]{"ALLOCATE", "200", "300"}));

        Exception ex = Assertions.assertThrows(InvalidInput.class, () -> SystematicInvestmentOperation.getInstance().processRequest(transactions, input), "Sample");
        Assert.assertEquals(ex.getMessage(), "[SystematicInvestmentOperation] [processRequest] [Exception] = invalid input parameter counts");
    }

    @Test
    void testInvalidRequestParameters() {
        TreeMap<Integer, java.util.LinkedList<Allocate>> transactions = new TreeMap<>();
        LinkedList<String> input = new LinkedList<String>(Arrays.asList(new String[]{"ALLOCATE", "a", "300", "500"}));

        Exception ex = Assertions.assertThrows(InvalidInput.class, () -> SystematicInvestmentOperation.getInstance().processRequest(transactions, input), "Sample");
        Assert.assertEquals(ex.getMessage(), "[SystematicInvestmentOperation] [processRequest] [Exception] = invalid input amounts");
    }

    @Test
    void testInvalidMonthAllocation() {
        TreeMap<Integer, java.util.LinkedList<Allocate>> transactions = new TreeMap<>();
        List<Integer> amounts = new ArrayList<>();
        Exception ex = Assertions.assertThrows(InvalidInput.class, () -> SystematicInvestmentOperation.getInstance().processAllocation(transactions, "JANUARY", amounts), "Sample");
        Assert.assertEquals(ex.getMessage(), "[SystematicInvestmentOperation] [processAllocation] [Exception] = invalid start month for sip");
    }

    @Test
    void testInvalidInputOrder() {
        TreeMap<Integer, java.util.LinkedList<Allocate>> transactions = new TreeMap<>();
        List<Integer> amounts = new ArrayList<>();
        Exception ex = Assertions.assertThrows(InvalidInput.class, () -> SystematicInvestmentOperation.getInstance().processAllocation(transactions, "FEBRUARY", amounts), "Sample");
        Assert.assertEquals(ex.getMessage(), "[SystematicInvestmentOperation] [processAllocation] [Exception] = no allocated amount before sip");
    }

    @Test
    void testValidProcessAllocation() throws InvalidInput {
        TreeMap<Integer, java.util.LinkedList<Allocate>> transactions = new TreeMap<>();
        LinkedList<Integer> amounts1 = new LinkedList<>(Arrays.asList(new Integer[]{200, 300, 500}));
        Allocate allocate1 = new Allocate(amounts1);

        LinkedList<Integer> amounts2 = new LinkedList<>(Arrays.asList(new Integer[]{300, 400, 600}));
        Allocate allocate2 = new Allocate(amounts2);

        LinkedList<Allocate> allocations = new LinkedList<>();
        allocations.add(allocate1);
        allocations.add(allocate2);

        transactions.put(0, allocations);
        List<Integer> amounts = new ArrayList<Integer>(Arrays.asList(new Integer[]{200, 300, 500}));
        SystematicInvestmentOperation.getInstance().processAllocation(transactions, "FEBRUARY", amounts);
        Assert.assertEquals(transactions.size(), 2);
        List<Integer> fund = transactions.lastEntry().getValue().getLast().getFundsValue();
        Assert.assertEquals((int) fund.get(0), 500);
        Assert.assertEquals((int) fund.get(1), 700);
        Assert.assertEquals((int) fund.get(2), 1100);
    }
}