package com.example.geektrust;

import com.example.geektrust.model.Portfolio;
import com.example.geektrust.service.PortfolioService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (String arg : args) {
            executorService.submit(() -> processInputFile(arg));
        }
    }

    private static void processInputFile(String filePath) {
        try {
            PortfolioService.getInstance().submitTask(new Portfolio(), filePath);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "[Main] [processInputFile] [Exception] = " + e.getMessage());
        }
    }
}