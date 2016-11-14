package com.dao.factories;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.dao.impl.BillDaoImpl;
import com.dao.impl.CarDaoImpl;
import com.dao.impl.ClientDaoImpl;
import com.dao.interfaces.BillDAO;
import com.dao.interfaces.CarDAO;
import com.dao.interfaces.ClientDAO;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class DaoFactoryMySQL {

    private static final String FICHIER_PROPERTIES = "/com/dao/factories/mysql.properties";
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "nomutilisateur";
    private static final String PROPERTY_MOT_DE_PASSE = "motdepasse";

    private String url;
    private String username;
    private String password;

    static BoneCP connectionPool = null;

    // DAOFactory( String url, String username, String password ) {
    // this.url = url;
    // this.username = username;
    // this.password = password;
    // }

    DaoFactoryMySQL(BoneCP connectionPool) {
	this.connectionPool = connectionPool;
    }

    /*
     * Méthode chargée de récupérer les informations de connexion à la base de
     * données, charger le driver JDBC et retourner une instance de la Factory
     */
    public static DaoFactoryMySQL getInstance() throws DAOConfigurationException {
	Properties properties = new Properties();
	String url;
	String driver;
	String nomUtilisateur;
	String motDePasse;

	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	InputStream fichierProperties = classLoader.getResourceAsStream(FICHIER_PROPERTIES);

	if (fichierProperties == null) {
	    throw new DAOConfigurationException("Le fichier properties " + FICHIER_PROPERTIES + " est introuvable.");
	}

	try {
	    properties.load(fichierProperties);
	    url = properties.getProperty(PROPERTY_URL);
	    driver = properties.getProperty(PROPERTY_DRIVER);
	    nomUtilisateur = properties.getProperty(PROPERTY_NOM_UTILISATEUR);
	    motDePasse = properties.getProperty(PROPERTY_MOT_DE_PASSE);
	} catch (IOException e) {
	    throw new DAOConfigurationException("Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e);
	}

	try {
	    Class.forName(driver);
	} catch (ClassNotFoundException e) {
	    throw new DAOConfigurationException("Le driver est introuvable dans le classpath.", e);
	}

	try {
	    /*
	     * Création d'une configuration de pool de connexions via l'objet
	     * BoneCPConfig et les différents setters associés.
	     */
	    BoneCPConfig config = new BoneCPConfig();
	    /* Mise en place de l'URL, du nom et du mot de passe */
	    config.setJdbcUrl(url);
	    config.setUsername(nomUtilisateur);
	    config.setPassword(motDePasse);
	    /* Paramétrage de la taille du pool */
	    config.setMinConnectionsPerPartition(5);
	    config.setMaxConnectionsPerPartition(10);
	    config.setPartitionCount(2);
	    /*
	     * Création du pool à partir de la configuration, via l'objet BoneCP
	     */
	    connectionPool = new BoneCP(config);
	} catch (SQLException e) {
	    e.printStackTrace();
	    throw new DAOConfigurationException("Erreur de configuration du pool de connexions.", e);
	}

	/*
	 * Enregistrement du pool créé dans une variable d'instance via un appel
	 * au constructeur de DAOFactory
	 */
	DaoFactoryMySQL instance = new DaoFactoryMySQL(connectionPool);
	return instance;

    }

    /* Méthode chargée de fournir une connexion à la base de données */
    public Connection getConnection() throws SQLException {
	return connectionPool.getConnection();
    }

    public ClientDAO getClientDao() {
	return new ClientDaoImpl(this);
    }

    public CarDAO getCarDao() {
	return new CarDaoImpl(this);
    }

    public BillDAO getBillDao() {
	return new BillDaoImpl(this);
    }
}
