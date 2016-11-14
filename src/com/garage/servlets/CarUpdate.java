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
import com.garage.forms.CreateCarForm;

@WebServlet(urlPatterns = "/updateCar")
public class CarUpdate extends HttpServlet {

    private static final String VIEW_FORM = "/WEB-INF/cars/update.jsp";
    private static final String VIEW_CARS = "voitures";

    private static final String FIELD_CAR_ID = "carId";

    public static final String ATT_CAR = "car";
    public static final String ATT_FORM = "form";

    public static final String CONF_DAO_FACTORY = "daofactory";
    private CarDAO carDao;

    public void init() throws ServletException {
	this.carDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getCarDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	String carId = req.getParameter(FIELD_CAR_ID);
	Long id;
	try {
	    id = Long.parseLong(carId);
	} catch (Exception e) {
	    id = 0L;
	}

	Car car = carDao.getById(id);

	req.setAttribute(ATT_CAR, car);

	this.getServletContext().getRequestDispatcher(VIEW_FORM).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	CreateCarForm form = new CreateCarForm(carDao);

	Car car = form.updateCar(req);

	req.setAttribute(ATT_CAR, car);
	req.setAttribute(ATT_FORM, form);

	if (form.getErreurs().isEmpty()) {
	    resp.sendRedirect(VIEW_CARS);
	} else {
	    this.getServletContext().getRequestDispatcher(VIEW_FORM).forward(req, resp);
	}
    }
}
