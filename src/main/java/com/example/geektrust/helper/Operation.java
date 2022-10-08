package com.example.geektrust.helper;

import com.example.geektrust.exception.InvalidInput;
import com.example.geektrust.model.Portfolio;

import java.util.LinkedList;
import java.util.List;

public interface Operation {

    List<Integer> processRequest(Portfolio portfolio, LinkedList<String> input) throws Exception;

    void processAllocation(Portfolio portfolio, String month, List<Integer> amount) throws InvalidInput;
}