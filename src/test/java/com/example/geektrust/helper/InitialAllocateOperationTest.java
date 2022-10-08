package com.example.geektrust.helper;

import com.example.geektrust.exception.InvalidInput;
import com.example.geektrust.model.Portfolio;
import edu.emory.mathcs.backport.java.util.Arrays;
import junit.framework.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class InitialAllocateOperationTest {

    @Test
    void testInvalidRequestParameterCount() {
        Portfolio portfolio = new Portfolio();
        LinkedList<String> input = new LinkedList<String>(Arrays.asList(new String[]{"ALLOCATE", "200", "300"}));

        Exception ex = Assertions.assertThrows(InvalidInput.class, () -> InitialAllocateOperation.getInstance().processRequest(portfolio, input), "Sample");
        Assert.assertEquals(ex.getMessage(), "[InitialAllocateOperation] [processRequest] [Exception] = invalid input parameter counts");
    }

    @Test
    void testInvalidRequestParameters() {
        Portfolio portfolio = new Portfolio();
        LinkedList<String> input = new LinkedList<String>(Arrays.asList(new String[]{"ALLOCATE", "a", "300", "500"}));

        Exception ex = Assertions.assertThrows(InvalidInput.class, () -> InitialAllocateOperation.getInstance().processRequest(portfolio, input), "Sample");
        Assert.assertEquals(ex.getMessage(), "[InitialAllocateOperation] [processRequest] [Exception] = invalid input amounts");
    }

    @Test
    void testValidProcessRequest() throws Exception {
        Portfolio portfolio = new Portfolio();
        LinkedList<String> input = new LinkedList<String>(Arrays.asList(new String[]{"ALLOCATE", "200", "300", "500"}));
        InitialAllocateOperation.getInstance().processRequest(portfolio, input);
        Assert.assertEquals(portfolio.getTransactions().size(), 1);
    }
}