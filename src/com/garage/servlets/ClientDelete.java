package com.garage.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.beans.Client;
import com.dao.factories.DaoFactoryMySQL;
import com.dao.interfaces.ClientDAO;

@WebServlet(urlPatterns = "/deleteClient")
public class ClientDelete extends HttpServlet {

    private static final String VIEW_FORM = "/WEB-INF/clients/update.jsp";
    private static final String VIEW_CLIENTS = "clients";

    private static final String FIELD_CUSTOMER_ID = "clientId";

    public static final String CONF_DAO_FACTORY = "daofactory";
    private ClientDAO clientDao;

    public void init() throws ServletException {
	this.clientDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getClientDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	String clientId = req.getParameter(FIELD_CUSTOMER_ID);
	Long id;
	try {
	    id = Long.parseLong(clientId);
	} catch (Exception e) {
	    id = 0L;
	}

	Client client = clientDao.getById(id);
	clientDao.deleteClient(client);

	resp.sendRedirect(VIEW_CLIENTS);
    }
}
