package fr.uga.miage.m1.exceptions;

public class StrategyException extends Exception {
    public StrategyException(String message) {
        super(message);
    }

    public StrategyException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
