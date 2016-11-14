package com.garage.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.beans.Car;
import com.dao.factories.DaoFactoryMySQL;
import com.dao.interfaces.CarDAO;

@WebServlet(urlPatterns = "/voitures")
public class CarList extends HttpServlet {

    private static final String VIEW = "/WEB-INF/cars/cars.jsp";

    private static final String ATT_CARS = "cars";

    public static final String CONF_DAO_FACTORY = "daofactory";
    private CarDAO carDao;

    public void init() throws ServletException {
	this.carDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getCarDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	List<Car> cars = carDao.getAll();

	req.setAttribute(ATT_CARS, cars);

	this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
    }

}
