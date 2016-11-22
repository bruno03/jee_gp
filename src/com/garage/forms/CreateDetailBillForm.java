package com.garage.forms;

import javax.servlet.http.HttpServletRequest;

import com.dao.beans.DetailBill;
import com.dao.factories.DAOException;
import com.dao.interfaces.DetailBillDAO;

public class CreateDetailBillForm extends CreateFormMaster {

    private static final String FIELD_QUANTITY = "quantity";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_UNIT_AMOUNT = "unitAmount";
    private static final String FIELD_FINAL_AMOUNT = "finalAmount";
    private static final String FIELD_BILL_ID = "billId";

    private DetailBillDAO billDetailDao;

    public CreateDetailBillForm(DetailBillDAO billDetailDao) {
	this.billDetailDao = billDetailDao;
    }

    public DetailBill createDetailBill(HttpServletRequest request) {

	String quantity = getValueField(request, FIELD_QUANTITY);
	String description = getValueField(request, FIELD_DESCRIPTION);
	String unitAmount = getValueField(request, FIELD_FINAL_AMOUNT);
	String finalAmount = getValueField(request, FIELD_FINAL_AMOUNT);
	String billId = getValueField(request, FIELD_BILL_ID);

	DetailBill detailBill = new DetailBill();

	try {
	    handleQuantity(quantity, detailBill);
	    handleDescription(description, detailBill);
	    handleUnitAmount(unitAmount, detailBill);
	    handleFinalAmount(finalAmount, detailBill);
	    handleBillId(billId, detailBill);

	    if (erreurs.isEmpty()) {
		billDetailDao.createBillDetail(detailBill);
		resultat = "Succès de la création détail facture.";
	    } else {
		resultat = "Échec de la création détail facture.";
	    }
	} catch (DAOException e) {
	    resultat = "Échec de la création détail facture : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
	    e.printStackTrace();
	}

	return detailBill;
    }

    private void handleQuantity(String quantity, DetailBill detailBill) {
	Integer quant = 0;
	try {
	    quant = validateQuantity(quantity);
	} catch (Exception e) {
	    setErreur(FIELD_QUANTITY, e.getMessage());
	}
	detailBill.setQuantity(quant);
    }

    private void handleDescription(String description, DetailBill detailBill) {
	try {

	} catch (Exception e) {
	    setErreur(FIELD_DESCRIPTION, e.getMessage());
	}
	detailBill.setDescription(description);
    }

    private void handleUnitAmount(String unitAmount, DetailBill detailBill) {
	Double amount = 0.0;
	try {
	    amount = validateAmount(unitAmount);
	} catch (Exception e) {
	    setErreur(FIELD_UNIT_AMOUNT, e.getMessage());
	}
	detailBill.setUnitAmount(amount);
    }

    private void handleFinalAmount(String finalAmount, DetailBill detailBill) {
	Double amount = 0.0;
	try {
	    amount = validateAmount(finalAmount);
	} catch (Exception e) {
	    setErreur(FIELD_FINAL_AMOUNT, e.getMessage());
	}
	detailBill.setFinalAmount(amount);
    }

    private void handleBillId(String billId, DetailBill detailBill) {
	Long id = 0L;
	try {
	    id = validateBill(billId);
	} catch (Exception e) {
	    setErreur(FIELD_BILL_ID, e.getMessage());
	}
	detailBill.setBillId(id);
    }

    private Integer validateQuantity(String quantity) throws FormValidationException {
	Integer kmFinal = -1;
	try {
	    kmFinal = Integer.parseInt(quantity);
	    if (kmFinal < 0) {
		throw new FormValidationException("la quantité ne peuvent pas être inférieurs à 0");
	    }
	} catch (NumberFormatException e) {
	    kmFinal = -1;
	    throw new FormValidationException("Problème sur le casting du champs : Quantité");
	}
	return kmFinal;
    }

    private void validateDescription(String description) throws FormValidationException {
	if (description.length() < 2)
	    throw new FormValidationException("La description doit contenir au moins 2 caractères");
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

    private Long validateBill(String billId) throws FormValidationException {
	Long id;
	if (billId != null) {
	    try {
		id = Long.parseLong(billId);
		if (id < 0) {
		    throw new FormValidationException("Le numéro BillID est négatif");
		}
	    } catch (NumberFormatException e) {
		id = -1L;
		throw new FormValidationException("Problème sur le casting du champs : BillID");
	    }
	} else {
	    id = -1L;
	    throw new FormValidationException("Aucun numéro de BillID");
	}
	return id;
    }
}
