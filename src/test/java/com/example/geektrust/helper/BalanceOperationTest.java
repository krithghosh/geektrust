package com.example.geektrust.helper;

import com.example.geektrust.exception.InvalidInput;
import com.example.geektrust.model.Allocate;
import com.example.geektrust.model.Portfolio;
import edu.emory.mathcs.backport.java.util.Arrays;
import junit.framework.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;

class BalanceOperationTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testInvalidBalanceForMonth() {
        Portfolio portfolio = new Portfolio();
        LinkedList<String> input = new LinkedList<String>(Arrays.asList(new String[]{"BALANCE", "JANUARY"}));

        Exception ex = Assertions.assertThrows(InvalidInput.class, () -> BalanceOperation.getInstance().processRequest(portfolio, input), "Sample");
        Assert.assertEquals(ex.getMessage(), "[BalanceOperation] [processRequest] [Exception] = no allocation for the month");
    }

    @Test
    void processRequest() throws Exception {
        Portfolio portfolio = new Portfolio();
        LinkedList<Integer> amounts1 = new LinkedList<>(Arrays.asList(new Integer[]{200, 300, 500}));
        Allocate allocate1 = new Allocate(amounts1);

        LinkedList<Integer> amounts2 = new LinkedList<>(Arrays.asList(new Integer[]{300, 400, 600}));
        Allocate allocate2 = new Allocate(amounts2);

        LinkedList<Allocate> allocations = new LinkedList<>();
        allocations.add(allocate1);
        allocations.add(allocate2);

        portfolio.getTransactions().put(0, allocations);

        LinkedList<String> input = new LinkedList<String>(Arrays.asList(new String[]{"BALANCE", "JANUARY"}));

        BalanceOperation.getInstance().processRequest(portfolio, input);
        Assert.assertEquals(outputStreamCaptor.toString().trim(), "300 400 600");
    }
}