package com.dao.impl;

import static com.dao.impl.DAOUtilitaire.fermeturesSilencieuses;
import static com.dao.impl.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dao.beans.Article;
import com.dao.factories.DAOException;
import com.dao.factories.DaoFactoryMySQL;
import com.dao.interfaces.ArticleDAO;
import com.dao.interfaces.CategoryDAO;

public class ArticleDaoImpl implements ArticleDAO {

    private DaoFactoryMySQL daoFactory;
    private CategoryDAO categoryDao;

    public ArticleDaoImpl(DaoFactoryMySQL daoFactory) {
	this.daoFactory = daoFactory;
	this.categoryDao = daoFactory.getCategoryDao();
    }

    private static final String SQL_SELECT = "SELECT id, description, montant_unitaire, categorie_id FROM article";

    @Override
    public List<Article> getAll() throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	List<Article> articles = new ArrayList<Article>();

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT, false);
	    resultSet = preparedStatement.executeQuery();
	    /*
	     * Parcours de la ligne de données de l'éventuel ResulSet retourné
	     */
	    while (resultSet.next()) {
		articles.add(map(resultSet));
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}

	return articles;
    }

    private static final String SQL_SELECT_BY_CATEGORY_ID = "SELECT id, description, montant_unitaire, categorie_id FROM article WHERE categorie_id = ?";

    @Override
    public List<Article> getByCategoryId(Long CategoryId) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	List<Article> articles = new ArrayList<Article>();

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_BY_CATEGORY_ID, false, CategoryId);
	    resultSet = preparedStatement.executeQuery();
	    /*
	     * Parcours de la ligne de données de l'éventuel ResulSet retourné
	     */
	    while (resultSet.next()) {
		articles.add(map(resultSet));
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}

	return articles;
    }

    private static final String SQL_SELECT_PAR_ID = "SELECT id, description, montant_unitaire, categorie_id FROM article WHERE id = ?";

    @Override
    public Article getById(Long id) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Article article = null;

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_ID, false, id);
	    resultSet = preparedStatement.executeQuery();
	    /*
	     * Parcours de la ligne de données de l'éventuel ResulSet retourné
	     */
	    if (resultSet.next()) {
		article = map(resultSet);
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}

	return article;
    }

    private static final String SQL_INSERT = "INSERT INTO Article (description, montant_unitaire, categorie_id) VALUES (?, ?, ?)";

    @Override
    public void createArticle(Article article) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet valeursAutoGenerees = null;

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, article.getDescription(),
		    article.getUnitAmount(), article.getCategoryId());
	    int statut = preparedStatement.executeUpdate();
	    /* Analyse du statut retourné par la requête d'insertion */
	    if (statut == 0) {
		throw new DAOException("Échec de la création de l'article, aucune ligne ajoutée dans la table.");
	    }
	    /* Récupération de l'id auto-généré par la requête d'insertion */
	    valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	    if (valeursAutoGenerees.next()) {
		/*
		 * Puis initialisation de la propriété id du bean Utilisateur
		 * avec sa valeur
		 */
		article.setId(valeursAutoGenerees.getLong(1));
	    } else {
		throw new DAOException("Échec de la création de l'article en base, aucun ID auto-généré retourné.");
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
	}

    }

    private static final String SQL_DELETE = "DELETE FROM article WHERE id = ?";

    @Override
    public void deleteArticle(Article article) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet valeursAutoGenerees = null;

	try {
	    /* Récupération d'une connexion depuis la Factory */
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE, false, article.getId());
	    int statut = preparedStatement.executeUpdate();
	    /* Analyse du statut retourné par la requête d'insertion */
	    if (statut == 0) {
		throw new DAOException("Échec de la suppression du article");
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
	}

    }

    private Article map(ResultSet resultSet) throws SQLException {
	Article article = new Article();
	article.setId(resultSet.getLong("id"));
	article.setDescription(resultSet.getString("description"));
	article.setUnitAmount(resultSet.getDouble("montant_unitaire"));
	article.setCategoryId(resultSet.getLong("categorie_id"));

	article.setCategory(categoryDao.getById(article.getCategoryId()));
	return article;
    }

}
