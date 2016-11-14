package com.garage.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.beans.Client;
import com.dao.factories.DaoFactoryMySQL;
import com.dao.interfaces.ClientDAO;

@WebServlet(urlPatterns = "/clients")
public class ClientList extends HttpServlet {

    private static final String VIEW = "/WEB-INF/clients/clients.jsp";

    private static final String ATT_CLIENTS = "clients";

    public static final String CONF_DAO_FACTORY = "daofactory";
    private ClientDAO clientDao;

    public void init() throws ServletException {
	this.clientDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getClientDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	List<Client> clients = clientDao.getAll();

	req.setAttribute(ATT_CLIENTS, clients);

	this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
    }
}
