package com.garage.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.beans.Bill;
import com.dao.beans.DetailBill;
import com.dao.factories.DaoFactoryMySQL;
import com.dao.interfaces.BillDAO;
import com.dao.interfaces.DetailBillDAO;

@WebServlet(urlPatterns = "/facture")
public class BillDetail extends HttpServlet {

    private static final String VIEW = "/WEB-INF/bills/detail.jsp";

    private static final String ATT_BILL = "bill";
    private static final String ID_FIELD = "billId";

    public static final String CONF_DAO_FACTORY = "daofactory";
    private BillDAO billDao;
    private DetailBillDAO detailBillDao;

    public void init() throws ServletException {
	this.billDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getBillDao();
	this.detailBillDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getBillDetailDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	String idString = req.getParameter(ID_FIELD);
	Long id = Long.parseLong(idString);

	Bill bill = billDao.getById(id);

	List<DetailBill> details = detailBillDao.getByBillId(bill.getId());
	bill.setDetails(details);

	req.setAttribute(ATT_BILL, bill);

	this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
    }
}
