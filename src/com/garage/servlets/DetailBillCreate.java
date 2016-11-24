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
import com.garage.forms.CreateDetailBillForm;

@WebServlet(urlPatterns = "/newDetailBill")
public class DetailBillCreate extends HttpServlet {

    private static final String VIEW = "/WEB-INF/bills/detail.jsp";

    private static final String ATT_BILL = "bill";
    private static final String ATT_FORM = "form";

    private static final String CONF_DAO_FACTORY = "daofactory";
    private DetailBillDAO detailBillDao;
    private BillDAO billDao;

    public void init() throws ServletException {
	this.detailBillDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getBillDetailDao();
	this.billDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getBillDao();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	CreateDetailBillForm form = new CreateDetailBillForm(detailBillDao);

	DetailBill detailBill = form.createDetailBill(req);

	Bill bill = billDao.getById(detailBill.getBillId());

	List<DetailBill> details = detailBillDao.getByBillId(bill.getId());
	bill.setDetails(details);

	bill.calculateAmountFinal();
	billDao.updateBill(bill);

	req.setAttribute(ATT_BILL, bill);

	if (form.getErreurs().isEmpty()) {
	    resp.sendRedirect("facture?billId=" + detailBill.getBillId());
	}

    }
}
