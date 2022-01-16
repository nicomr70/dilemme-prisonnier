package fr.uga.miage.m1.exceptions;

public class GameException extends Exception {

    public GameException(String message) {
        super(message);
    }

    public GameException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
