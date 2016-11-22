package com.dao.impl;

import static com.dao.impl.DAOUtilitaire.fermeturesSilencieuses;
import static com.dao.impl.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dao.beans.DetailBill;
import com.dao.factories.DAOException;
import com.dao.factories.DaoFactoryMySQL;
import com.dao.interfaces.DetailBillDAO;

public class DetailBillDaoImpl implements DetailBillDAO {

    private DaoFactoryMySQL daoFactory;

    public DetailBillDaoImpl(DaoFactoryMySQL daoFactory) {
	this.daoFactory = daoFactory;
    }

    private static final String SQL_SELECT = "SELECT id, quantite, description, montant_unitaire, montant_final, facture_id FROM facture_detail";

    @Override
    public List<DetailBill> getAll() throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	List<DetailBill> billDetails = new ArrayList<DetailBill>();

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT, false);
	    resultSet = preparedStatement.executeQuery();
	    /*
	     * Parcours de la ligne de données de l'éventuel ResulSet retourné
	     */
	    while (resultSet.next()) {
		billDetails.add(map(resultSet));
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}

	return billDetails;
    }

    private static final String SQL_SELECT_BY_ID = "SELECT id, quantite, description, montant_unitaire, montant_final, facture_id FROM facture_detail WHERE id = ?";

    @Override
    public DetailBill getById(Long id) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	DetailBill billDetail = null;

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_BY_ID, false, id);
	    resultSet = preparedStatement.executeQuery();
	    /*
	     * Parcours de la ligne de données de l'éventuel ResulSet retourné
	     */
	    if (resultSet.next()) {
		billDetail = map(resultSet);
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}

	return billDetail;
    }

    private static final String SQL_SELECT_BY_BILL_ID = "SELECT id, quantite, description, montant_unitaire, montant_final, facture_id FROM facture_detail WHERE facture_id = ?";

    @Override
    public List<DetailBill> getByBillId(Long id) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	List<DetailBill> billDetails = new ArrayList<DetailBill>();

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_BY_BILL_ID, false, id);
	    resultSet = preparedStatement.executeQuery();
	    /*
	     * Parcours de la ligne de données de l'éventuel ResulSet retourné
	     */
	    while (resultSet.next()) {
		billDetails.add(map(resultSet));
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}

	return billDetails;
    }

    private static final String SQL_INSERT = "INSERT INTO facture_detail (quantite, description, montant_unitaire, montant_final, facture_id) VALUES (?, ?, ?, ?, ?)";

    @Override
    public void createBillDetail(DetailBill billDetail) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet valeursAutoGenerees = null;

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, billDetail.getQuantity(),
		    billDetail.getDescription(), billDetail.getUnitAmount(), billDetail.getFinalAmount(),
		    billDetail.getBillId());
	    int statut = preparedStatement.executeUpdate();
	    /* Analyse du statut retourné par la requête d'insertion */
	    if (statut == 0) {
		throw new DAOException(
			"Échec de la création de la facture detail, aucune ligne ajoutée dans la table.");
	    }
	    /* Récupération de l'id auto-généré par la requête d'insertion */
	    valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	    if (valeursAutoGenerees.next()) {
		/*
		 * Puis initialisation de la propriété id du bean Utilisateur
		 * avec sa valeur
		 */
		billDetail.setId(valeursAutoGenerees.getLong(1));
	    } else {
		throw new DAOException(
			"Échec de la création de la facture detail en base, aucun ID auto-généré retourné.");
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
	}

    }

    private static final String SQL_UPDATE = "UPDATE facture_detail SET quantite=?, description =?, montant_unitaire=?, montant_final=? facture_id=? WHERE id=?";

    @Override
    public void updateBillDetail(DetailBill billDetail) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet valeursAutoGenerees = null;

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, false, billDetail.getQuantity(),
		    billDetail.getDescription(), billDetail.getUnitAmount(), billDetail.getFinalAmount(),
		    billDetail.getBillId(), billDetail.getId());
	    int statut = preparedStatement.executeUpdate();
	    /* Analyse du statut retourné par la requête d'insertion */
	    if (statut == 0) {
		throw new DAOException("Échec de la modification de la facture detail");
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
	}

    }

    private static final String SQL_DELETE = "DELETE FROM facture_id WHERE id = ?";

    @Override
    public void deleteBillDetail(DetailBill billDetail) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet valeursAutoGenerees = null;

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE, false, billDetail.getId());
	    int statut = preparedStatement.executeUpdate();
	    /* Analyse du statut retourné par la requête d'insertion */
	    if (statut == 0) {
		throw new DAOException("Échec de la suppression de la facture detail");
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
	}

    }

    private static DetailBill map(ResultSet resultSet) throws SQLException {
	DetailBill billDetail = new DetailBill();
	billDetail.setId(resultSet.getLong("id"));
	billDetail.setQuantity(resultSet.getDouble("quantite"));
	billDetail.setDescription(resultSet.getString("description"));
	billDetail.setUnitAmount(resultSet.getDouble("montant_unitaire"));
	billDetail.setFinalAmount(resultSet.getDouble("montant_final"));
	billDetail.setBillId(resultSet.getLong("facture_id"));
	return billDetail;
    }

}
