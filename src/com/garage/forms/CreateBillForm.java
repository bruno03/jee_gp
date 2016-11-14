package com.garage.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;

import com.dao.beans.Bill;
import com.dao.factories.DAOException;
import com.dao.interfaces.BillDAO;

public class CreateBillForm {

    private static final String FIELD_DATE = "dateBill";
    private static final String FIELD_CLIENT_ID = "clientIdBill";
    private static final String FIELD_CAR_ID = "carIdBill";
    private static final String FIELD_KM = "kmBill";
    private static final String FIELD_AMOUNT = "amountBill";

    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();

    private BillDAO billDao;

    public CreateBillForm(BillDAO billDao) {
	this.billDao = billDao;
    }

    public String getResultat() {
	return resultat;
    }

    public Map<String, String> getErreurs() {
	return erreurs;
    }

    public Bill createBill(HttpServletRequest request) {

	String date = getValueField(request, FIELD_DATE);
	String clientId = getValueField(request, FIELD_CLIENT_ID);
	String carId = getValueField(request, FIELD_CAR_ID);
	String km = getValueField(request, FIELD_KM);
	String amount = getValueField(request, FIELD_AMOUNT);

	Bill bill = new Bill();
	try {
	    handleDate(date, bill);
	    handleClientId(clientId, bill);
	    handleCarId(carId, bill);
	    handleKm(km, bill);
	    handleAmount(amount, bill);

	    if (erreurs.isEmpty()) {
		billDao.createBill(bill);
		resultat = "Succès de la création facture.";
	    } else {
		resultat = "Échec de la création facture.";
	    }
	} catch (DAOException e) {
	    resultat = "Échec de la création facture : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
	    e.printStackTrace();
	}

	return bill;
    }

    private void handleDate(String date, Bill bill) {
	DateTime dt = null;
	try {
	    validateNull(date);
	    dt = validateDate(date);
	} catch (Exception e) {
	    setErreur(FIELD_DATE, e.getMessage());
	}
	bill.setDate(dt);
    }

    private void handleClientId(String clientId, Bill bill) {
	Long id = -1L;
	try {
	    id = validateClient(clientId);
	} catch (Exception e) {
	    setErreur(FIELD_CLIENT_ID, e.getMessage());
	}
	bill.setClientId(id);
    }

    private void handleCarId(String carId, Bill bill) {
	Long id = -1L;
	try {
	    id = validateClient(carId);
	} catch (Exception e) {
	    setErreur(FIELD_CAR_ID, e.getMessage());
	}
	bill.setCarId(id);
    }

    private void handleKm(String km, Bill bill) {
	Integer kmFinal = -1;
	try {
	    validateNull(km);
	    kmFinal = validateKm(km);
	} catch (Exception e) {
	    setErreur(FIELD_KM, e.getMessage());
	}
	bill.setKm(kmFinal);
    }

    private void handleAmount(String amount, Bill bill) {
	Double amountFinal = -1.0;
	try {
	    validateNull(amount);
	    amountFinal = validateAmount(amount);
	} catch (Exception e) {
	    setErreur(FIELD_AMOUNT, e.getMessage());
	}
	bill.setAmount(amountFinal);
    }

    private void validateNull(String value) throws FormValidationException {
	if (value == "") {
	    throw new FormValidationException("ce champs est requis");
	}
    }

    private DateTime validateDate(String date) throws FormValidationException {
	DateTime dt = null;

	try {
	    dt = DateTime.parse(date);

	} catch (IllegalArgumentException e) {
	    dt = DateTime.parse("20000101");
	    throw new FormValidationException("La date n'est pas valide");
	}

	return dt;
    }

    private Long validateClient(String clientId) throws FormValidationException {
	Long id;
	if (clientId != null) {
	    try {
		id = Long.parseLong(clientId);
		if (id < 0) {
		    throw new FormValidationException("Le numéro ClientID est négatif");
		}
	    } catch (NumberFormatException e) {
		id = -1L;
		throw new FormValidationException("Problème sur le casting du champs : ClientID");
	    }
	} else {
	    id = -1L;
	    throw new FormValidationException("Aucun numéro de clientID");
	}
	return id;
    }

    private Integer validateKm(String km) throws FormValidationException {
	Integer kmFinal = -1;
	try {
	    kmFinal = Integer.parseInt(km);
	    if (kmFinal < 0) {
		throw new FormValidationException("les km ne peuvent pas être inférieurs à 0");
	    }
	    if (kmFinal > 1000000000) {
		throw new FormValidationException("les km ne peuvent pas être supérieurs à 1'000'000'000 km");
	    }
	} catch (NumberFormatException e) {
	    kmFinal = -1;
	    throw new FormValidationException("Problème sur le casting du champs : Km");
	}
	return kmFinal;
    }

    private Double validateAmount(String amount) throws FormValidationException {
	Double amountFinal = -1.0;
	try {
	    amountFinal = Double.parseDouble(amount);
	    if (amountFinal < 0) {
		throw new FormValidationException("le montant ne peut pas être inférieur à 0");
	    }

	} catch (NumberFormatException e) {
	    amountFinal = -1.0;
	    throw new FormValidationException("Problème sur le casting du champs : Montant");
	}
	return amountFinal;
    }

    private void setErreur(String champ, String message) {
	erreurs.put(champ, message);
    }

    private static String getValueField(HttpServletRequest request, String nomChamp) {
	String valeur = request.getParameter(nomChamp);
	if (valeur == null || valeur.trim().length() == 0) {
	    return "";
	} else {
	    return valeur.trim();
	}
    }
}
