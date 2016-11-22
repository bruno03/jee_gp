package com.garage.forms;

import javax.servlet.http.HttpServletRequest;

import com.dao.beans.Article;
import com.dao.factories.DAOException;
import com.dao.interfaces.ArticleDAO;

public class CreateArticleForm extends CreateFormMaster {

    private static final String FIELD_DESCRIPTION = "descriptionArticle";
    private static final String FIELD_MONTANT = "unitAmountArticle";
    private static final String FIELD_CATEGORY_ID = "categoryIdArticle";

    private ArticleDAO articleDao;

    public CreateArticleForm(ArticleDAO articleDao) {
	this.articleDao = articleDao;
    }

    public Article createArticle(HttpServletRequest request) {

	String description = getValueField(request, FIELD_DESCRIPTION);
	String montant = getValueField(request, FIELD_MONTANT);
	String categoryId = getValueField(request, FIELD_CATEGORY_ID);

	Article article = new Article();

	try {
	    handleDescription(description, article);
	    handleMontant(montant, article);
	    handleCategoryId(categoryId, article);

	    if (erreurs.isEmpty()) {
		articleDao.createArticle(article);
		resultat = "Succès de la création de l'article.";
	    } else {
		resultat = "Échec de la création de l'article.";
	    }
	} catch (DAOException e) {
	    resultat = "Échec de la création de l'article : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
	    e.printStackTrace();
	}

	return article;
    }

    private void handleDescription(String description, Article article) {
	try {
	    validateDescriptionField(description);
	    validateLengthField(description, 100);
	} catch (Exception e) {
	    setErreur(FIELD_DESCRIPTION, e.getMessage());
	}
	article.setDescription(description);
    }

    private void handleMontant(String montant, Article article) {
	Double amount = 0.0;
	try {
	    amount = validateAmount(montant);
	} catch (Exception e) {
	    setErreur(FIELD_MONTANT, e.getMessage());
	}

	article.setUnitAmount(amount);
    }

    private void handleCategoryId(String categoryId, Article article) {
	Long id = 0L;
	try {
	    id = validateCategory(categoryId);
	} catch (Exception e) {
	    setErreur(FIELD_CATEGORY_ID, e.getMessage());
	}

	article.setCategoryId(id);
    }

    private void validateDescriptionField(String description) throws FormValidationException {
	if (description.length() < 2) {
	    throw new FormValidationException("La description doit contenir au moins 2 caractères");
	}
    }

    private Long validateCategory(String categoryId) throws FormValidationException {
	Long id;
	if (categoryId != null) {
	    try {
		id = Long.parseLong(categoryId);
		if (id < 0) {
		    throw new FormValidationException("Le numéro categoryId est négatif");
		}
	    } catch (NumberFormatException e) {
		id = -1L;
		throw new FormValidationException("Problème sur le casting du champs : categoryId");
	    }
	} else {
	    id = -1L;
	    throw new FormValidationException("Aucun numéro de categoryId");
	}
	return id;
    }
}
