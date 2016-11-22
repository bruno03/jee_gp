package com.dao.impl;

import static com.dao.impl.DAOUtilitaire.fermeturesSilencieuses;
import static com.dao.impl.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dao.beans.Category;
import com.dao.factories.DAOException;
import com.dao.factories.DaoFactoryMySQL;
import com.dao.interfaces.CategoryDAO;

public class CategoryDaoImpl implements CategoryDAO {

    private DaoFactoryMySQL daoFactory;

    public CategoryDaoImpl(DaoFactoryMySQL daoFactory) {
	this.daoFactory = daoFactory;
    }

    private static final String SQL_SELECT = "SELECT id, nom FROM categorie";

    @Override
    public List<Category> getAll() throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	List<Category> categories = new ArrayList<Category>();

	try {
	    /* R�cup�ration d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT, false);
	    resultSet = preparedStatement.executeQuery();
	    /*
	     * Parcours de la ligne de donn�es de l'�ventuel ResulSet retourn�
	     */
	    while (resultSet.next()) {
		categories.add(map(resultSet));
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}

	return categories;
    }

    private static final String SQL_INSERT = "INSERT INTO categorie (nom) VALUES (?)";

    @Override
    public void createCategory(Category category) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet valeursAutoGenerees = null;

	try {
	    /* R�cup�ration d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, category.getName());
	    int statut = preparedStatement.executeUpdate();
	    /* Analyse du statut retourn� par la requ�te d'insertion */
	    if (statut == 0) {
		throw new DAOException("�chec de la cr�ation de la categorie, aucune ligne ajout�e dans la table.");
	    }
	    /* R�cup�ration de l'id auto-g�n�r� par la requ�te d'insertion */
	    valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	    if (valeursAutoGenerees.next()) {
		/*
		 * Puis initialisation de la propri�t� id du bean Utilisateur
		 * avec sa valeur
		 */
		category.setId(valeursAutoGenerees.getLong(1));
	    } else {
		throw new DAOException("�chec de la cr�ation de la categorie en base, aucun ID auto-g�n�r� retourn�.");
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
	}

    }

    private static final String SQL_DELETE = "DELETE FROM categorie WHERE id = ?";

    @Override
    public void deleteCategory(Category category) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet valeursAutoGenerees = null;

	try {
	    /* R�cup�ration d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE, false, category.getId());
	    int statut = preparedStatement.executeUpdate();
	    /* Analyse du statut retourn� par la requ�te d'insertion */
	    if (statut == 0) {
		throw new DAOException("�chec de la suppression du categorie");
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
	}

    }

    private static Category map(ResultSet resultSet) throws SQLException {
	Category category = new Category();
	category.setId(resultSet.getLong("id"));
	category.setName(resultSet.getString("nom"));
	return category;
    }

}
