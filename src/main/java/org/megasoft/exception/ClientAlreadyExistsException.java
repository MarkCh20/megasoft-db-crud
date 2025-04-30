package org.megasoft.exception;

public class ClientAlreadyExistsException extends RuntimeException {
    public ClientAlreadyExistsException(String name) {
        super("Client with name '" + name + "' already exists.");
    }
}
