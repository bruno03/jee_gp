package com.garage.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.beans.Article;
import com.dao.beans.Category;
import com.dao.factories.DaoFactoryMySQL;
import com.dao.interfaces.ArticleDAO;
import com.dao.interfaces.CategoryDAO;
import com.garage.forms.CreateArticleForm;

@WebServlet(urlPatterns = "/newArticle")
public class ArticleCreate extends HttpServlet {

    private static final String VIEW_FORM = "/WEB-INF/articles/create.jsp";
    private static final String REDIRECT_ARTICLES = "articles";

    private static final String ATT_ARTICLE = "article";
    private static final String ATT_CATEGORIES = "categories";
    private static final String ATT_FORM = "form";

    private static final String CONF_DAO_FACTORY = "daofactory";
    private ArticleDAO articleDao;
    private CategoryDAO categoryDao;

    public void init() throws ServletException {
	this.articleDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getArticleDao();
	this.categoryDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getCategoryDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	List<Category> categories = categoryDao.getAll();

	req.setAttribute(ATT_CATEGORIES, categories);

	this.getServletContext().getRequestDispatcher(VIEW_FORM).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	List<Category> categories = categoryDao.getAll();
	CreateArticleForm form = new CreateArticleForm(articleDao);

	Article article = form.createArticle(req);

	req.setAttribute(ATT_ARTICLE, article);
	req.setAttribute(ATT_FORM, form);
	req.setAttribute(ATT_CATEGORIES, categories);

	if (form.getErreurs().isEmpty()) {
	    resp.sendRedirect(REDIRECT_ARTICLES);
	} else {
	    this.getServletContext().getRequestDispatcher(VIEW_FORM).forward(req, resp);
	}
    }
}
