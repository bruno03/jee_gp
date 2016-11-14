package com.dao.impl;

import static com.dao.impl.DAOUtilitaire.fermeturesSilencieuses;
import static com.dao.impl.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dao.beans.Client;
import com.dao.factories.DAOException;
import com.dao.factories.DaoFactoryMySQL;
import com.dao.interfaces.CarDAO;
import com.dao.interfaces.ClientDAO;

public class ClientDaoImpl implements ClientDAO {

    private DaoFactoryMySQL daoFactory;
    private static CarDAO carDao;

    public ClientDaoImpl(DaoFactoryMySQL daoFactory) {
	this.daoFactory = daoFactory;
	// this.carDao = daoFactory.getCarDao();
    }

    private static final String SQL_SELECT = "SELECT id, nom, prenom, adresse, cp, ville FROM client";

    @Override
    public List<Client> getAll() throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	List<Client> clients = new ArrayList<Client>();

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT, false);
	    resultSet = preparedStatement.executeQuery();
	    /*
	     * Parcours de la ligne de données de l'éventuel ResulSet retourné
	     */
	    while (resultSet.next()) {
		clients.add(map(resultSet));
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}

	return clients;
    }

    private static final String SQL_SELECT_PAR_ID = "SELECT id, nom, prenom, adresse, cp, ville FROM Client WHERE id = ?";

    @Override
    public Client getById(Long id) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Client client = null;

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_ID, false, id);
	    resultSet = preparedStatement.executeQuery();
	    /*
	     * Parcours de la ligne de données de l'éventuel ResulSet retourné
	     */
	    if (resultSet.next()) {
		client = map(resultSet);
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}

	return client;
    }

    private static final String SQL_INSERT = "INSERT INTO Client (nom, prenom, adresse, cp, ville) VALUES (?, ?, ?, ?, ?)";

    @Override
    public void createClient(Client client) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet valeursAutoGenerees = null;

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, client.getLastname(),
		    client.getFirstname(), client.getAddresse(), client.getCp(), client.getCity());
	    int statut = preparedStatement.executeUpdate();
	    /* Analyse du statut retourné par la requête d'insertion */
	    if (statut == 0) {
		throw new DAOException("Échec de la création du client, aucune ligne ajoutée dans la table.");
	    }
	    /* Récupération de l'id auto-généré par la requête d'insertion */
	    valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	    if (valeursAutoGenerees.next()) {
		/*
		 * Puis initialisation de la propriété id du bean Utilisateur
		 * avec sa valeur
		 */
		client.setId(valeursAutoGenerees.getLong(1));
	    } else {
		throw new DAOException("Échec de la création du client en base, aucun ID auto-généré retourné.");
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
	}
    }

    private static final String SQL_UPDATE = "UPDATE Client SET nom=?, prenom =?, adresse=?, cp=?, ville=? WHERE id=?";

    @Override
    public void updateClient(Client client) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet valeursAutoGenerees = null;

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, false, client.getLastname(),
		    client.getFirstname(), client.getAddresse(), client.getCp(), client.getCity(), client.getId());
	    int statut = preparedStatement.executeUpdate();
	    /* Analyse du statut retourné par la requête d'insertion */
	    if (statut == 0) {
		throw new DAOException("Échec de la modification du client");
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
	}

    }

    private static final String SQL_DELETE = "DELETE FROM client WHERE id = ?";

    @Override
    public void deleteClient(Client client) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet valeursAutoGenerees = null;

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE, false, client.getId());
	    int statut = preparedStatement.executeUpdate();
	    /* Analyse du statut retourné par la requête d'insertion */
	    if (statut == 0) {
		throw new DAOException("Échec de la suppression du client");
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
	}

    }

    private static Client map(ResultSet resultSet) throws SQLException {
	Client client = new Client();
	client.setId(resultSet.getLong("id"));
	client.setLastname(resultSet.getString("nom"));
	client.setFirstname(resultSet.getString("prenom"));
	client.setAddresse(resultSet.getString("adresse"));
	client.setCp(resultSet.getString("cp"));
	client.setCity(resultSet.getString("ville"));

	// client.setCars(carDao.getCarsByClientId(client.getId()));

	return client;
    }
}
