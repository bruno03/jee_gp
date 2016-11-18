package com.garage.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.beans.Client;
import com.dao.beans.DataSearch;
import com.dao.factories.DaoFactoryMySQL;
import com.dao.interfaces.ClientDAO;
import com.google.gson.Gson;

@WebServlet(urlPatterns = "/searchClient")
public class ClientSearchJson extends HttpServlet {

    private static final String FIELD_VALUE_SEARCH = "searchValue";

    public static final String CONF_DAO_FACTORY = "daofactory";
    private ClientDAO clientDao;

    public void init() throws ServletException {
	this.clientDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getClientDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	String value = req.getParameter(FIELD_VALUE_SEARCH);

	List<Client> clients = clientDao.getNameStartWith(value);

	List<DataSearch> list = new ArrayList<DataSearch>();

	for (int i = 0; i < clients.size(); i++) {
	    list.add(new DataSearch(clients.get(i).getFullName(), clients.get(i).getId().toString()));
	}

	String json = new Gson().toJson(list);

	resp.setContentType("application/json");
	resp.setCharacterEncoding("UTF8");
	resp.getWriter().write(json);
    }
}
