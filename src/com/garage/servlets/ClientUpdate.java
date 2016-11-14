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
import com.garage.forms.CreateClientForm;

@WebServlet(urlPatterns = "/updateClient")
public class ClientUpdate extends HttpServlet {

    private static final String VIEW_FORM = "/WEB-INF/clients/update.jsp";
    private static final String VIEW_CLIENTS = "clients";

    private static final String FIELD_CUSTOMER_ID = "clientId";

    public static final String ATT_CLIENT = "client";
    public static final String ATT_FORM = "form";

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

	req.setAttribute(ATT_CLIENT, client);

	this.getServletContext().getRequestDispatcher(VIEW_FORM).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	CreateClientForm form = new CreateClientForm(clientDao);

	Client client = form.updateClient(req);

	req.setAttribute(ATT_CLIENT, client);
	req.setAttribute(ATT_FORM, form);

	if (form.getErreurs().isEmpty()) {
	    resp.sendRedirect(VIEW_CLIENTS);
	} else {
	    this.getServletContext().getRequestDispatcher(VIEW_FORM).forward(req, resp);
	}
    }
}
