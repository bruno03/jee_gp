package com.dao.interfaces;

import java.util.List;

import com.dao.beans.Client;
import com.dao.factories.DAOException;

public interface ClientDAO {

    List<Client> getAll() throws DAOException;

    List<Client> getNameStartWith(String value) throws DAOException;

    Client getById(Long id) throws DAOException;

    void createClient(Client client) throws DAOException;

    void updateClient(Client client) throws DAOException;

    void deleteClient(Client client) throws DAOException;

}
