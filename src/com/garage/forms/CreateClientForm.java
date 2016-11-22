package com.garage.forms;

import javax.servlet.http.HttpServletRequest;

import com.dao.beans.Client;
import com.dao.factories.DAOException;
import com.dao.interfaces.ClientDAO;

public class CreateClientForm extends CreateFormMaster {

    private static final String FIELD_FIRSTNAME = "firstnameClient";
    private static final String FIELD_LASTNAME = "lastnameClient";
    private static final String FIELD_ADDRESSE = "adresseClient";
    private static final String FIELD_CP = "cpClient";
    private static final String FIELD_CITY = "cityClient";
    private static final String FIELD_CLIENT_ID = "idClient";

    private ClientDAO clientDao;

    public CreateClientForm(ClientDAO clientDao) {
	this.clientDao = clientDao;
    }

    public Client creerClient(HttpServletRequest request) {

	String lastname = getValueField(request, FIELD_LASTNAME);
	String firstname = getValueField(request, FIELD_FIRSTNAME);
	String adresse = getValueField(request, FIELD_ADDRESSE);
	String cp = getValueField(request, FIELD_CP);
	String city = getValueField(request, FIELD_CITY);

	Client client = new Client();
	try {
	    handleLastname(lastname, client);
	    handleFirstname(firstname, client);
	    handleAddresse(adresse, client);
	    handleCp(cp, client);
	    handleCity(city, client);

	    if (erreurs.isEmpty()) {
		clientDao.createClient(client);
		resultat = "Succès de la création client.";
	    } else {
		resultat = "Échec de la création client.";
	    }
	} catch (DAOException e) {
	    resultat = "Échec de la création client : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
	    e.printStackTrace();
	}

	return client;
    }

    public Client updateClient(HttpServletRequest request) {
	String lastname = getValueField(request, FIELD_LASTNAME);
	String firstname = getValueField(request, FIELD_FIRSTNAME);
	String adresse = getValueField(request, FIELD_ADDRESSE);
	String cp = getValueField(request, FIELD_CP);
	String city = getValueField(request, FIELD_CITY);
	String id = getValueField(request, FIELD_CLIENT_ID);

	Client client = new Client();
	try {

	    handleLastname(lastname, client);
	    handleFirstname(firstname, client);
	    handleAddresse(adresse, client);
	    handleCp(cp, client);
	    handleCity(city, client);
	    handleClientId(id, client);

	    if (erreurs.isEmpty()) {
		clientDao.updateClient(client);
		resultat = "Succès de la modification du client.";
	    } else {
		resultat = "Échec de la modification du client.";
	    }
	} catch (DAOException e) {
	    resultat = "Échec de la modification du client : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
	    e.printStackTrace();
	}

	return client;
    }

    private void handleLastname(String nom, Client client) {
	try {
	    validateLastname(nom);
	} catch (Exception e) {
	    setErreur(FIELD_LASTNAME, e.getMessage());
	}
	client.setLastname(nom);
    }

    private void handleFirstname(String prenom, Client client) {
	try {
	    validateLengthField(prenom, 40);
	} catch (Exception e) {
	    setErreur(FIELD_FIRSTNAME, e.getMessage());
	}

	client.setFirstname(prenom);
    }

    private void handleAddresse(String adresse, Client client) {
	try {
	    validateLengthField(adresse, 40);
	} catch (Exception e) {
	    setErreur(FIELD_ADDRESSE, e.getMessage());
	}
	client.setAddresse(adresse);
    }

    private void handleCp(String cp, Client client) {
	try {
	    validateLengthField(cp, 10);
	} catch (Exception e) {
	    setErreur(FIELD_CP, e.getMessage());
	}
	client.setCp(cp);
    }

    private void handleCity(String city, Client client) {
	try {
	    validateLengthField(city, 20);
	} catch (Exception e) {
	    setErreur(FIELD_CITY, e.getMessage());
	}
	client.setCity(city);
    }

    private void handleClientId(String id, Client client) {
	Long clientId = null;
	try {
	    clientId = validateClientId(id);
	} catch (Exception e) {
	    setErreur(FIELD_CLIENT_ID, "le numéro client n'est pas valable");
	}
	client.setId(clientId);
    }

    private void validateLastname(String name) throws FormValidationException {
	if (name == null) {
	    throw new FormValidationException("Ce champs est réquis");
	}

	if (name.length() < 2 || name.length() > 40) {
	    throw new FormValidationException("minimum 2 caractères, maximum 40 caractères");
	}
    }

    private Long validateClientId(String id) throws FormValidationException {
	Long clientId = -1L;

	try {
	    clientId = Long.parseLong(id);
	    if (clientId < 0) {
		throw new FormValidationException("Le numéro ClientID est négatif");
	    }
	} catch (NumberFormatException e) {
	    clientId = -1L;
	    throw new FormValidationException("Problème de casting sur le numéro ClientID");
	}

	return clientId;
    }

}
