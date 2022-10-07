package com.example.geektrust.exception;

public class InvalidInput extends Exception {
    public InvalidInput(String errorMessage, String className, String methodName) {
        super(String.format("[%s] [%s] [Exception] = %s", className, methodName, errorMessage));
    }
}