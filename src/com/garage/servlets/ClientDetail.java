package com.garage.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.beans.Car;
import com.dao.beans.Client;
import com.dao.factories.DaoFactoryMySQL;
import com.dao.interfaces.CarDAO;
import com.dao.interfaces.ClientDAO;

@WebServlet(urlPatterns = "/client")
public class ClientDetail extends HttpServlet {

    private static final String VIEW = "/WEB-INF/clients/detail.jsp";

    private static final String ATT_CLIENT = "client";
    private static final String ID_FIELD = "clientId";

    public static final String CONF_DAO_FACTORY = "daofactory";
    private ClientDAO clientDao;
    private CarDAO carDao;

    public void init() throws ServletException {
	this.clientDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getClientDao();
	this.carDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getCarDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	String idString = req.getParameter(ID_FIELD);
	Long id = Long.parseLong(idString);

	Client client = clientDao.getById(id);
	List<Car> cars = carDao.getCarsByClientId(client.getId());
	client.setCars(cars);

	req.setAttribute(ATT_CLIENT, client);

	this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
    }
}
