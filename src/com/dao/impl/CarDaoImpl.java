package com.dao.impl;

import static com.dao.impl.DAOUtilitaire.fermeturesSilencieuses;
import static com.dao.impl.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dao.beans.Car;
import com.dao.factories.DAOException;
import com.dao.factories.DaoFactoryMySQL;
import com.dao.interfaces.CarDAO;
import com.dao.interfaces.ClientDAO;

public class CarDaoImpl implements CarDAO {

    private DaoFactoryMySQL daoFactory;
    private static ClientDAO clientDao;

    public CarDaoImpl(DaoFactoryMySQL daoFactory) {
	this.daoFactory = daoFactory;
	this.clientDao = daoFactory.getClientDao();
    }

    private static final String SQL_SELECT = "SELECT id, marque, modele, plaque, client_id FROM voiture";

    @Override
    public List<Car> getAll() throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	List<Car> cars = new ArrayList<Car>();

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT, false);
	    resultSet = preparedStatement.executeQuery();
	    /*
	     * Parcours de la ligne de données de l'éventuel ResulSet retourné
	     */
	    while (resultSet.next()) {
		cars.add(map(resultSet));
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}

	return cars;
    }

    private static final String SQL_SELECT_BY_ID = "SELECT id, marque, modele, plaque, client_id FROM voiture WHERE id = ?";

    @Override
    public Car getById(Long id) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Car car = null;

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_BY_ID, false, id);
	    resultSet = preparedStatement.executeQuery();
	    /*
	     * Parcours de la ligne de données de l'éventuel ResulSet retourné
	     */
	    if (resultSet.next()) {
		car = map(resultSet);
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}

	return car;
    }

    private static final String SQL_SELECT_BY_CUSTOMER_ID = "SELECT id, marque, modele, plaque, client_id FROM voiture WHERE client_id = ?";

    @Override
    public List<Car> getCarsByClientId(Long id) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	List<Car> cars = new ArrayList<Car>();

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_BY_CUSTOMER_ID, false, id);
	    resultSet = preparedStatement.executeQuery();
	    /*
	     * Parcours de la ligne de données de l'éventuel ResulSet retourné
	     */
	    while (resultSet.next()) {
		cars.add(map(resultSet));
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}

	return cars;
    }

    private static final String SQL_INSERT = "INSERT INTO voiture (marque, modele, plaque, client_id) VALUES (?, ?, ?, ?)";

    @Override
    public void createCar(Car car) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet valeursAutoGenerees = null;

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, car.getBrand(),
		    car.getModel(), car.getImmatriculation(), car.getClientId());
	    int statut = preparedStatement.executeUpdate();
	    /* Analyse du statut retourné par la requête d'insertion */
	    if (statut == 0) {
		throw new DAOException("Échec de la création de la voiture, aucune ligne ajoutée dans la table.");
	    }
	    /* Récupération de l'id auto-généré par la requête d'insertion */
	    valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	    if (valeursAutoGenerees.next()) {
		/*
		 * Puis initialisation de la propriété id du bean Utilisateur
		 * avec sa valeur
		 */
		car.setId(valeursAutoGenerees.getLong(1));
	    } else {
		throw new DAOException("Échec de la création de la voiture en base, aucun ID auto-généré retourné.");
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
	}
    }

    private static Car map(ResultSet resultSet) throws SQLException {
	Car car = new Car();
	car.setId(resultSet.getLong("id"));
	car.setBrand(resultSet.getString("marque"));
	car.setModel(resultSet.getString("modele"));
	car.setImmatriculation(resultSet.getString("plaque"));
	car.setClientId(resultSet.getLong("client_id"));
	car.setClient(clientDao.getById(resultSet.getLong("client_id")));
	return car;
    }

}
