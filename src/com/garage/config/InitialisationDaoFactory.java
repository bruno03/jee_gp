package com.garage.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dao.factories.DaoFactoryMySQL;

public class InitialisationDaoFactory implements ServletContextListener {

    private static final String ATT_DAO_FACTORY = "daofactory";

    private DaoFactoryMySQL daoFactory;

    @Override
    public void contextInitialized(ServletContextEvent event) {
	/* Récupération du ServletContext lors du chargement de l'application */
	ServletContext servletContext = event.getServletContext();
	/* Instanciation de notre DAOFactory */
	this.daoFactory = DaoFactoryMySQL.getInstance();
	/*
	 * Enregistrement dans un attribut ayant pour portée toute l'application
	 */
	servletContext.setAttribute(ATT_DAO_FACTORY, this.daoFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
	/* Rien à réaliser lors de la fermeture de l'application... */
    }
}
