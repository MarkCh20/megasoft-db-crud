package org.megasoft.exception;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(long id) {
        super("Client with ID " + id + " not found.");
    }
}
