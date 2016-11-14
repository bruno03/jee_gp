package com.dao.impl;

import static com.dao.impl.DAOUtilitaire.fermeturesSilencieuses;
import static com.dao.impl.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.dao.beans.Bill;
import com.dao.factories.DAOException;
import com.dao.factories.DaoFactoryMySQL;
import com.dao.interfaces.BillDAO;

public class BillDaoImpl implements BillDAO {

    private DaoFactoryMySQL daoFactory;
    // private static ClientDAO clientDao;

    public BillDaoImpl(DaoFactoryMySQL daoFactory) {
	this.daoFactory = daoFactory;
	// this.clientDao = daoFactory.getClientDao();
    }

    private static final String SQL_SELECT = "SELECT id, date, client_id, voiture_id, km, montant FROM facture";

    @Override
    public List<Bill> getAll() throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	List<Bill> bills = new ArrayList<Bill>();

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT, false);
	    resultSet = preparedStatement.executeQuery();
	    /*
	     * Parcours de la ligne de données de l'éventuel ResulSet retourné
	     */
	    while (resultSet.next()) {
		bills.add(map(resultSet));
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}

	return bills;
    }

    private static final String SQL_SELECT_BY_ID = "SELECT id, date, client_id, voiture_id, km, montant FROM facture WHERE id = ?";

    @Override
    public Bill getById(Long id) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Bill bill = new Bill();

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_BY_ID, false, id);
	    resultSet = preparedStatement.executeQuery();
	    /*
	     * Parcours de la ligne de données de l'éventuel ResulSet retourné
	     */
	    while (resultSet.next()) {
		bill = map(resultSet);
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}

	return bill;
    }

    private static final String SQL_SELECT_BY_CLIENT_ID = "SELECT id, date, client_id, voiture_id, km, montant FROM facture WHERE client_id = ?";

    @Override
    public List<Bill> getByClientId(Long id) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	List<Bill> bills = new ArrayList<Bill>();

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_BY_CLIENT_ID, false, id);
	    resultSet = preparedStatement.executeQuery();
	    /*
	     * Parcours de la ligne de données de l'éventuel ResulSet retourné
	     */
	    while (resultSet.next()) {
		bills.add(map(resultSet));
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}

	return bills;
    }

    private static final String SQL_SELECT_BY_CAR_ID = "SELECT id, date, client_id, voiture_id, km, montant FROM facture WHERE voiture_id = ?";

    @Override
    public List<Bill> getByCarId(Long id) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	List<Bill> bills = new ArrayList<Bill>();

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_BY_CLIENT_ID, false, id);
	    resultSet = preparedStatement.executeQuery();
	    /*
	     * Parcours de la ligne de données de l'éventuel ResulSet retourné
	     */
	    while (resultSet.next()) {
		bills.add(map(resultSet));
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}

	return bills;
    }

    private static final String SQL_INSERT = "INSERT INTO facture (date, client_id, voiture_id, km, montant) VALUES (?, ?, ?, ?, ?)";

    @Override
    public void createBill(Bill bill) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet valeursAutoGenerees = null;

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true,
		    new Timestamp(bill.getDate().getMillis()), bill.getClientId(), bill.getCarId(), bill.getKm(),
		    bill.getAmount());
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
		bill.setId(valeursAutoGenerees.getLong(1));
	    } else {
		throw new DAOException("Échec de la création de la voiture en base, aucun ID auto-généré retourné.");
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
	}

    }

    @Override
    public void updateBill(Bill bill) throws DAOException {
	// TODO Auto-generated method stub

    }

    @Override
    public void deleteBill(Bill bill) throws DAOException {
	// TODO Auto-generated method stub

    }

    private static Bill map(ResultSet resultSet) throws SQLException {
	Bill bill = new Bill();
	bill.setId(resultSet.getLong("id"));
	bill.setDate(new DateTime(resultSet.getTimestamp("date")));
	bill.setClientId(resultSet.getLong("client_id"));
	bill.setCarId(resultSet.getLong("voiture_id"));
	bill.setKm(resultSet.getInt("km"));
	bill.setAmount(resultSet.getDouble("montant"));
	return bill;
    }
}
