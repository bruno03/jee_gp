package com.garage.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public abstract class CreateFormMaster {

    protected String resultat;
    protected Map<String, String> erreurs = new HashMap<String, String>();

    public String getResultat() {
	return resultat;
    }

    public Map<String, String> getErreurs() {
	return erreurs;
    }

    protected void setErreur(String champ, String message) {
	erreurs.put(champ, message);
    }

    protected static String getValueField(HttpServletRequest request, String nomChamp) {
	String valeur = request.getParameter(nomChamp);
	if (valeur == null || valeur.trim().length() == 0) {
	    return "";
	} else {
	    return valeur.trim();
	}
    }

    protected void validateLengthField(String field, int maxLength) throws FormValidationException {
	if (field.length() > maxLength) {
	    throw new FormValidationException("maximum " + maxLength + " caractères");
	}
    }

    protected Double validateAmount(String amount) throws FormValidationException {
	Double amountFinal = -1.0;
	try {
	    amountFinal = Double.parseDouble(amount);
	    if (amountFinal < 0) {
		throw new FormValidationException("le montant ne peut pas être inférieur à 0");
	    }

	} catch (NumberFormatException e) {
	    amountFinal = null;
	    throw new FormValidationException("Problème sur le casting du champs : Montant");
	}
	return amountFinal;
    }
}
