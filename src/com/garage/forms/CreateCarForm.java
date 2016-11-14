package com.garage.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dao.beans.Car;
import com.dao.factories.DAOException;
import com.dao.interfaces.CarDAO;

public class CreateCarForm {

    private static final String FIELD_BRAND = "brandCar";
    private static final String FIELD_MODEL = "modelCar";
    private static final String FIELD_IMMATRICULATION = "immatriculationCar";
    private static final String FIELD_CLIENT_ID = "clientIdCar";
    private static final String FIELD_CAR_ID = "carId";

    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();

    private CarDAO carDao;

    public CreateCarForm(CarDAO carDao) {
	this.carDao = carDao;
    }

    public String getResultat() {
	return resultat;
    }

    public Map<String, String> getErreurs() {
	return erreurs;
    }

    public Car creerCar(HttpServletRequest request) {

	String brand = getValueField(request, FIELD_BRAND);
	String model = getValueField(request, FIELD_MODEL);
	String immatriculation = getValueField(request, FIELD_IMMATRICULATION);
	String clientId = getValueField(request, FIELD_CLIENT_ID);

	Car car = new Car();
	try {
	    handleBrand(brand, car);
	    handleModel(model, car);
	    handleImmatriculation(immatriculation, car);
	    handleClientId(clientId, car);

	    if (erreurs.isEmpty()) {
		carDao.createCar(car);
		resultat = "Succès de la création voiture.";
	    } else {
		resultat = "Échec de la création voiture.";
	    }
	} catch (DAOException e) {
	    resultat = "Échec de la création voiture : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
	    e.printStackTrace();
	}

	return car;
    }

    public Car updateCar(HttpServletRequest request) {

	String brand = getValueField(request, FIELD_BRAND);
	String model = getValueField(request, FIELD_MODEL);
	String immatriculation = getValueField(request, FIELD_IMMATRICULATION);
	String clientId = getValueField(request, FIELD_CLIENT_ID);
	String idCar = getValueField(request, FIELD_CAR_ID);

	Car car = new Car();
	try {
	    handleBrand(brand, car);
	    handleModel(model, car);
	    handleImmatriculation(immatriculation, car);
	    handleClientId(clientId, car);
	    handleCarId(idCar, car);

	    if (erreurs.isEmpty()) {
		carDao.updateCar(car);
		resultat = "Succès de la modification voiture.";
	    } else {
		resultat = "Échec de la modification voiture.";
	    }
	} catch (DAOException e) {
	    resultat = "Échec de la modification voiture : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
	    e.printStackTrace();
	}

	return car;
    }

    private void handleCarId(String idCar, Car car) {
	Long id = -1L;
	try {
	    id = validateClient(idCar);
	} catch (Exception e) {
	    setErreur(FIELD_CLIENT_ID, e.getMessage());
	}
	car.setId(id);
    }

    private void handleBrand(String brand, Car car) {
	try {
	    validateBrand(brand);
	    validateLengthField(brand, 30);
	} catch (Exception e) {
	    setErreur(FIELD_BRAND, e.getMessage());
	}
	car.setBrand(brand);
    }

    private void handleModel(String model, Car car) {
	try {
	    validateLengthField(model, 30);
	} catch (Exception e) {
	    setErreur(FIELD_MODEL, e.getMessage());
	}
	car.setModel(model);
    }

    private void handleImmatriculation(String immatriculation, Car car) {
	try {
	    validateLengthField(immatriculation, 20);
	} catch (Exception e) {
	    setErreur(FIELD_IMMATRICULATION, e.getMessage());
	}
	car.setImmatriculation(immatriculation);
    }

    private void handleClientId(String clientId, Car car) {
	Long id = -1L;
	try {
	    id = validateClient(clientId);
	} catch (Exception e) {
	    setErreur(FIELD_CLIENT_ID, e.getMessage());
	}
	car.setClientId(id);
    }

    private void validateBrand(String brand) throws FormValidationException {
	if (brand == null) {
	    throw new FormValidationException("Ce champs est requis");
	}

	if (brand.length() < 2) {
	    throw new FormValidationException("minimum 2 caractères");
	}
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

    private void validateLengthField(String field, int maxLength) throws FormValidationException {
	if (field.length() > maxLength) {
	    throw new FormValidationException("maximum " + maxLength + " caractères");
	}
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
