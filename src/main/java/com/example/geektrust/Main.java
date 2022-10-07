package com.example.geektrust;

import com.example.geektrust.service.Portfolio;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        processInputFile(args[0]);
    }

    private static void processInputFile(String filePath) {
        try {
            Portfolio portfolio = new Portfolio();
            Files.readAllLines(Paths.get(filePath)).forEach(portfolio::executeTransaction);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "[Main] [processInputFile] [Exception] = " + e.getMessage());
        }
    }
}