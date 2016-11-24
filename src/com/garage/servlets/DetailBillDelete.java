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

@WebServlet(urlPatterns = "/deleteDetailBill")
public class DetailBillDelete extends HttpServlet {

    private static final String REDIRECTION = "facture?billId=";

    private static final String FIELD_DETAIL_ID = "detailId";

    private static final String ATT_BILL = "bill";

    private static final String CONF_DAO_FACTORY = "daofactory";
    private DetailBillDAO detailBillDao;
    private BillDAO billDao;

    public void init() throws ServletException {
	this.detailBillDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getBillDetailDao();
	this.billDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getBillDao();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	String detailId = req.getParameter(FIELD_DETAIL_ID);
	Long id;
	try {
	    id = Long.parseLong(detailId);
	} catch (Exception e) {
	    id = 0L;
	}

	DetailBill detail = detailBillDao.getById(id);
	detailBillDao.deleteBillDetail(detail);

	Bill bill = billDao.getById(detail.getBillId());

	List<DetailBill> details = detailBillDao.getByBillId(bill.getId());
	bill.setDetails(details);

	bill.calculateAmountFinal();
	billDao.updateBill(bill);

	req.setAttribute(ATT_BILL, bill);

	resp.sendRedirect(REDIRECTION + detail.getBillId());

    }

}
