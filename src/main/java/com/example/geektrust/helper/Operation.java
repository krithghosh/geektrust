package com.example.geektrust.helper;

import com.example.geektrust.exception.InvalidInput;
import com.example.geektrust.model.Allocate;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public interface Operation {

    List<Integer> processRequest(TreeMap<Integer, LinkedList<Allocate>> transactions, LinkedList<String> input) throws Exception;

    void processAllocation(TreeMap<Integer, LinkedList<Allocate>> transactions, String month, List<Integer> amount) throws InvalidInput;
}
