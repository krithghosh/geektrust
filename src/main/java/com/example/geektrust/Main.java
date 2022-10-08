package com.example.geektrust;

import com.example.geektrust.model.Portfolio;
import com.example.geektrust.service.PortfolioService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        processInputFile(args[0]);
        System.exit(0);
    }

    private static void processInputFile(String filePath) {
        try {
            PortfolioService.getInstance().submitTask(new Portfolio(), filePath);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "[Main] [processInputFile] [Exception] = " + e.getMessage());
        }
    }
}