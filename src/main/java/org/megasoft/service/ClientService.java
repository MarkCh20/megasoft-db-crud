package org.megasoft.service;

import org.megasoft.dao.ClientDao;
import org.megasoft.model.Client;
import org.megasoft.exception.ClientAlreadyExistsException;

import java.util.List;

public class ClientService {

    private final ClientDao clientDao;

    public ClientService() {
        this.clientDao = new ClientDao();
    }

    public long create(String name) {
        validateName(name);
        if (clientDao.existsByName(name)) {
            throw new ClientAlreadyExistsException(name);
        }
        return clientDao.create(name);
    }

    public String getById(long id) {
        return clientDao.getById(id).getName();
    }

    public void setName(long id, String name) {
        validateName(name);
        clientDao.updateName(id, name);
    }

    public void deleteById(long id) {
        clientDao.deleteById(id);
    }

    public List<Client> listAll() {
        return clientDao.listAll();
    }

    private void validateName(String name) {
        if (name == null || name.length() < 2 || name.length() > 100) {
            throw new IllegalArgumentException("Client name must be between 2 and 100 characters.");
        }
    }
}
