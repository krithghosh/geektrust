package com.example.geektrust.helper;

import com.example.geektrust.exception.InvalidInput;
import com.example.geektrust.model.Allocate;
import com.example.geektrust.model.Portfolio;
import edu.emory.mathcs.backport.java.util.Arrays;
import junit.framework.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

class ChangeOperationTest {

    @Test
    void testInvalidRequestParameterCount() {
        Portfolio portfolio = new Portfolio();
        LinkedList<String> input = new LinkedList<String>(Arrays.asList(new String[]{"CHANGE", "20%", "30%"}));

        Exception ex = Assertions.assertThrows(InvalidInput.class, () -> ChangeOperation.getInstance().processRequest(portfolio, input), "Sample");
        Assert.assertEquals(ex.getMessage(), "[ChangeOperation] [processRequest] [Exception] = invalid input parameter counts");
    }

    @Test
    void testValidProcessRequest() throws Exception {
        Portfolio portfolio = new Portfolio();
        LinkedList<Integer> amounts1 = new LinkedList<>(Arrays.asList(new Integer[]{200, 300, 500}));
        Allocate allocate1 = new Allocate(amounts1);

        LinkedList<Allocate> allocations = new LinkedList<>();
        allocations.add(allocate1);

        portfolio.getTransactions().put(0, allocations);

        LinkedList<String> input = new LinkedList<String>(Arrays.asList(new String[]{"CHANGE", "10%", "20%", "30%", "JANUARY"}));
        List<Integer> output = ChangeOperation.getInstance().processRequest(portfolio, input);
        Assert.assertEquals((int) output.get(0), 220);
        Assert.assertEquals((int) output.get(1), 360);
        Assert.assertEquals((int) output.get(2), 650);
    }
}