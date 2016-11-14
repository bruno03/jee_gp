package com.garage.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.beans.Car;
import com.dao.factories.DaoFactoryMySQL;
import com.dao.interfaces.CarDAO;

@WebServlet(urlPatterns = "/voiture")
public class CarDetail extends HttpServlet {

    private static final String VIEW = "/WEB-INF/cars/detail.jsp";

    private static final String ATT_CAR = "car";
    private static final String ID_FIELD = "carId";

    public static final String CONF_DAO_FACTORY = "daofactory";
    private CarDAO carDao;

    public void init() throws ServletException {
	this.carDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getCarDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	String idString = req.getParameter(ID_FIELD);

	Long id = Long.parseLong(idString);

	Car car = carDao.getById(id);

	req.setAttribute(ATT_CAR, car);

	this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
    }

}
