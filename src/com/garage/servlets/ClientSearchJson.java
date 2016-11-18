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
import com.google.gson.Gson;

@WebServlet(urlPatterns = "/searchClient")
public class ClientSearchJson extends HttpServlet {

    private static final String FIELD_VALUE_SEARCH = "searchValue";

    public static final String CONF_DAO_FACTORY = "daofactory";
    private ClientDAO clientDao;
    private CarDAO carDao;

    public void init() throws ServletException {
	this.clientDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getClientDao();
	this.carDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getCarDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	String value = req.getParameter(FIELD_VALUE_SEARCH);

	List<Client> clients = clientDao.getNameStartWith(value);

	for (int i = 0; i < clients.size(); i++) {
	    List<Car> cars = carDao.getCarsByClientId(clients.get(i).getId());
	    clients.get(i).setCars(cars);
	}

	String json = new Gson().toJson(clients);

	resp.setContentType("application/json");
	resp.setCharacterEncoding("UTF8");
	resp.getWriter().write(json);
    }
}
