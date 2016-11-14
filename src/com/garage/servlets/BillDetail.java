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

@WebServlet(urlPatterns = "/facture")
public class BillDetail extends HttpServlet {

    private static final String VIEW = "/WEB-INF/bills/detail.jsp";

    private static final String ATT_BILL = "bill";
    private static final String ID_FIELD = "billId";

    public static final String CONF_DAO_FACTORY = "daofactory";
    private BillDAO billDao;

    public void init() throws ServletException {
	this.billDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getBillDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	String idString = req.getParameter(ID_FIELD);
	Long id = Long.parseLong(idString);

	Bill bill = billDao.getById(id);

	req.setAttribute(ATT_BILL, bill);

	this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
    }
}
