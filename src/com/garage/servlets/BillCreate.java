package com.garage.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.beans.Bill;
import com.dao.factories.DaoFactoryMySQL;
import com.dao.interfaces.BillDAO;
import com.dao.interfaces.CarDAO;
import com.garage.forms.CreateBillForm;

@WebServlet(urlPatterns = "/newBill")
public class BillCreate extends HttpServlet {

    private static final String VIEW_FORM = "/WEB-INF/bills/create.jsp";
    private static final String VIEW_FORM2 = "/WEB-INF/bills/newBill.jsp";
    private static final String VIEW_BILLS = "factures";

    private static final String FIELD_CAR_ID = "carId";

    private static final String ATT_BILL = "bill";
    private static final String ATT_FORM = "form";

    private static final String CONF_DAO_FACTORY = "daofactory";
    private BillDAO billDao;
    private CarDAO carDao;

    public void init() throws ServletException {
	this.billDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getBillDao();
	this.carDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getCarDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	// String carId = req.getParameter(FIELD_CAR_ID);
	// Long id;
	// try {
	// id = Long.parseLong(carId);
	// } catch (Exception e) {
	// id = 0L;
	// }
	// Car car = carDao.getById(id);
	//
	// Bill bill = new Bill();
	// bill.setCar(car);
	// bill.setCarId(car.getId());
	// bill.setClient(car.getClient());
	// bill.setClientId(car.getClientId());
	//
	// req.setAttribute(ATT_BILL, bill);

	this.getServletContext().getRequestDispatcher(VIEW_FORM2).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	CreateBillForm form = new CreateBillForm(billDao);

	Bill bill = form.createBill(req);

	req.setAttribute(ATT_BILL, bill);
	req.setAttribute(ATT_FORM, form);

	if (form.getErreurs().isEmpty()) {
	    resp.sendRedirect(VIEW_BILLS);
	} else {
	    this.getServletContext().getRequestDispatcher(VIEW_FORM2).forward(req, resp);
	}
    }
}
