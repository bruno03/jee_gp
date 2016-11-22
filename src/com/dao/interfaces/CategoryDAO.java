package com.dao.interfaces;

import java.util.List;

import com.dao.beans.Category;
import com.dao.factories.DAOException;

public interface CategoryDAO {

    List<Category> getAll() throws DAOException;

    Category getById(Long id) throws DAOException;

    void createCategory(Category category) throws DAOException;

    void deleteCategory(Category category) throws DAOException;
}
