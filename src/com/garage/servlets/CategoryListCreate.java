package com.garage.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.beans.Category;
import com.dao.factories.DaoFactoryMySQL;
import com.dao.interfaces.CategoryDAO;
import com.garage.forms.CreateCategoryForm;

@WebServlet(urlPatterns = "/categories")
public class CategoryListCreate extends HttpServlet {

    private static final String VIEW = "/WEB-INF/categories/categories.jsp";
    private static final String VIEW_CATS = "categories";

    private static final String ATT_CATEGORIES = "categories";
    private static final String ATT_CAT = "cat";
    private static final String ATT_FORM = "form";

    public static final String CONF_DAO_FACTORY = "daofactory";
    private CategoryDAO categoryDao;

    public void init() throws ServletException {
	this.categoryDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getCategoryDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	List<Category> categories = categoryDao.getAll();

	req.setAttribute(ATT_CATEGORIES, categories);

	this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	CreateCategoryForm form = new CreateCategoryForm(categoryDao);

	Category category = form.createCategory(req);

	req.setAttribute(ATT_CAT, category);
	req.setAttribute(ATT_FORM, form);

	if (form.getErreurs().isEmpty()) {
	    resp.sendRedirect(VIEW_CATS);
	} else {
	    this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
	}
    }
}
