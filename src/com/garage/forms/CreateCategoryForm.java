package com.garage.forms;

import javax.servlet.http.HttpServletRequest;

import com.dao.beans.Category;
import com.dao.factories.DAOException;
import com.dao.interfaces.CategoryDAO;

public class CreateCategoryForm extends CreateFormMaster {

    private static final String FIELD_NAME = "nameCat";

    private CategoryDAO categoryDao;

    public CreateCategoryForm(CategoryDAO categoryDao) {
	this.categoryDao = categoryDao;
    }

    public Category createCategory(HttpServletRequest request) {
	String name = getValueField(request, FIELD_NAME);

	Category category = new Category();

	try {
	    handleName(name, category);

	    if (erreurs.isEmpty()) {
		categoryDao.createCategory(category);
		resultat = "Succ�s de la cr�ation cat�gorie.";
	    } else {
		resultat = "�chec de la cr�ation cat�gorie.";
	    }
	} catch (DAOException e) {
	    resultat = "�chec de la cr�ation cat�gorie : une erreur impr�vue est survenue, merci de r�essayer dans quelques instants.";
	    e.printStackTrace();
	}

	return category;
    }

    private void handleName(String name, Category category) {
	try {
	    validateNameField(name);
	    validateLengthField(name, 100);
	} catch (Exception e) {
	    setErreur(FIELD_NAME, e.getMessage());
	}

	category.setName(name);
    }

    private void validateNameField(String name) throws FormValidationException {
	if (name.length() < 2) {
	    throw new FormValidationException("Le champs doit contenir au minimum 2 caract�res");
	}
    }

}
