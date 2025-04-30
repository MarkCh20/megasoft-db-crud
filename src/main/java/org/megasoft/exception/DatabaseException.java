package org.megasoft.exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String action, Throwable cause) {
        super("Database error occurred while " + action + ": " + cause.getMessage(), cause);
    }
}
