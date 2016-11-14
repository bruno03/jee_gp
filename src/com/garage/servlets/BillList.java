package com.garage.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.beans.Bill;
import com.dao.factories.DaoFactoryMySQL;
import com.dao.interfaces.BillDAO;

@WebServlet(urlPatterns = "/factures")
public class BillList extends HttpServlet {

    private static final String VIEW = "/WEB-INF/bills/bills.jsp";

    private static final String ATT_BILLS = "bills";

    public static final String CONF_DAO_FACTORY = "daofactory";
    private BillDAO billDao;

    public void init() throws ServletException {
	this.billDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getBillDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	List<Bill> bills = billDao.getAll();

	req.setAttribute(ATT_BILLS, bills);

	this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
    }
}
