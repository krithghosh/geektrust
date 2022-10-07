package com.example.geektrust.exception;

public class InvalidCommand extends Exception {
    public InvalidCommand(String errorMessage, String className, String methodName) {
        super(String.format("[%s] [%s] [Exception] = %s", className, methodName, errorMessage));
    }
}