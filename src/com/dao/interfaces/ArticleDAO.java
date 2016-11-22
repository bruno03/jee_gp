package com.dao.interfaces;

import java.util.List;

import com.dao.beans.Article;
import com.dao.factories.DAOException;

public interface ArticleDAO {

    List<Article> getAll() throws DAOException;

    List<Article> getByCategoryId(Long CategoryId) throws DAOException;

    Article getById(Long id) throws DAOException;

    void createArticle(Article article) throws DAOException;

    void deleteArticle(Article article) throws DAOException;

}
