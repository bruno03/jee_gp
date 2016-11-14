
<div class="form-group">
	<label for="lastnameClient">Nom</label>
	<input type="text" class="form-control" id="lastnameClient" 
	name="lastnameClient" placeholder="Nom" value="<c:out value="${ client.lastname }"/>" aria-describedby="lastnameClient2">
	<span id="lastnameClient2" class="help-block"><c:out value="${ form.erreurs['lastnameClient'] }" /></span>
</div>

<div class="form-group">
	<label for="firstnameClient">Pr�nom</label>
	<input type="text" class="form-control" id="firstnameClient" 
	name="firstnameClient" value="<c:out value="${ client.firstname }"/>" placeholder="Pr�nom" aria-describedby="firstnameClient2">
	<span id="firstnameClient2" class="help-block"><c:out value="${ form.erreurs['firstnameClient'] }" /></span>
</div>

<div class="form-group">
	<label for="adresseClient">Adresse</label>
	<input type="text" class="form-control" id="adresseClient"
	 name="adresseClient" value="<c:out value="${ client.addresse }"/>" placeholder="Adresse" aria-describedby="adresseClient2">
	<span id="adresseClient2" class="help-block"><c:out value="${ form.erreurs['adresseClient'] }" /></span>		
</div>

<div class="form-group">
	<label for="cpClient">Code postal</label>
	<input type="text" class="form-control" id="cpClient" 
	name="cpClient" value="<c:out value="${ client.cp }"/>" placeholder="CP" aria-describedby="cpClient2">
	<span id="cpClient2" class="help-block"><c:out value="${ form.erreurs['cpClient'] }" /></span>

</div>

<div class="form-group">
	<label for="cityClient">Ville</label>
	<input type="text" class="form-control" id="cityClient" 
	name="cityClient" value="<c:out value="${ client.city }"/>" placeholder="Ville" aria-describedby="cityClient2">	
	<span id="cityClient2" class="help-block"><c:out value="${ form.erreurs['cityClient'] }" /></span>
</div>