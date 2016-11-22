package com.garage.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.beans.Article;
import com.dao.factories.DaoFactoryMySQL;
import com.dao.interfaces.ArticleDAO;
import com.dao.interfaces.CategoryDAO;

@WebServlet(urlPatterns = "/articles")
public class ArticleList extends HttpServlet {
    private static final String VIEW = "/WEB-INF/articles/articles.jsp";

    private static final String ATT_ARTICLES = "articles";

    public static final String CONF_DAO_FACTORY = "daofactory";
    private ArticleDAO articleDao;
    private CategoryDAO categoryDao;

    public void init() throws ServletException {
	this.articleDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getArticleDao();
	this.categoryDao = ((DaoFactoryMySQL) getServletContext().getAttribute(CONF_DAO_FACTORY)).getCategoryDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	List<Article> articles = articleDao.getAll();

	req.setAttribute(ATT_ARTICLES, articles);

	this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
    }
}
