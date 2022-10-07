package com.example.geektrust.helper;

import com.example.geektrust.exception.InvalidInput;
import com.example.geektrust.model.Allocate;
import edu.emory.mathcs.backport.java.util.Arrays;
import junit.framework.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.TreeMap;

class InitialAllocateOperationTest {

    @Test
    void testInvalidRequestParameterCount() {
        TreeMap<Integer, java.util.LinkedList<Allocate>> transactions = new TreeMap<>();
        LinkedList<String> input = new LinkedList<String>(Arrays.asList(new String[]{"ALLOCATE", "200", "300"}));

        Exception ex = Assertions.assertThrows(InvalidInput.class, () -> InitialAllocateOperation.getInstance().processRequest(transactions, input), "Sample");
        Assert.assertEquals(ex.getMessage(), "[InitialAllocateOperation] [processRequest] [Exception] = invalid input parameter counts");
    }

    @Test
    void testInvalidRequestParameters() {
        TreeMap<Integer, java.util.LinkedList<Allocate>> transactions = new TreeMap<>();
        LinkedList<String> input = new LinkedList<String>(Arrays.asList(new String[]{"ALLOCATE", "a", "300", "500"}));

        Exception ex = Assertions.assertThrows(InvalidInput.class, () -> InitialAllocateOperation.getInstance().processRequest(transactions, input), "Sample");
        Assert.assertEquals(ex.getMessage(), "[InitialAllocateOperation] [processRequest] [Exception] = invalid input amounts");
    }

    @Test
    void testValidProcessRequest() throws Exception {
        TreeMap<Integer, java.util.LinkedList<Allocate>> transactions = new TreeMap<>();
        LinkedList<String> input = new LinkedList<String>(Arrays.asList(new String[]{"ALLOCATE", "200", "300", "500"}));
        InitialAllocateOperation.getInstance().processRequest(transactions, input);
        Assert.assertEquals(transactions.size(), 1);
    }
}