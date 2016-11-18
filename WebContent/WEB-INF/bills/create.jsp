<%@ page 
pageEncoding="UTF-8" 
language="java"
contentType="text/html; 
charset=UTF-8"
%>
	

	<c:import url="/inc/header.jsp"></c:import>
	<c:import url="/inc/menu.jsp"></c:import>

	<div class="container">
	
	<h1>Nouvelle Facture : </h1>
		<form method="post" action="newBill">	
			<div class="form-group">
				<label for="dateBill">Date</label>
				<input type="text" class="form-control" id="dateBill" 
				name="dateBill" placeholder="" value="<c:out value="${ bill.date }"/>" aria-describedby="dateBill2">
				<span id="dateBill2" class="help-block"><c:out value="${ form.erreurs['dateBill'] }" /></span>
			</div>
			
			<div class="form-group">
				<label for="clientIdBill">Client ID</label>
				<input type="text" class="form-control" id="clientIdBill" 
				name="clientIdBill" value="<c:out value="${ bill.clientId }"/>" placeholder="" aria-describedby="clientIdBill2">
				<span id="clientIdBill2" class="help-block"><c:out value="${ form.erreurs['clientIdBill'] }" /></span>
			</div>
			
			<div class="form-group">
				<label for="carIdBill">Voiture ID</label>
				<input type="text" class="form-control" id="carIdBill"
				 name="carIdBill" value="<c:out value="${ bill.carId }"/>" placeholder="" aria-describedby="carIdBill2">
				<span id="carIdBill2" class="help-block"><c:out value="${ form.erreurs['carIdBill'] }" /></span>		
			</div>
			
			<div class="form-group">
				<label for="kmBill">KM</label>
				<input type="number" class="form-control" id="kmBill"
				 name="kmBill" value="<c:out value="${ bill.km }"/>" placeholder="Km" aria-describedby="kmBill2">
				<span id="kmBill2" class="help-block"><c:out value="${ form.erreurs['kmBill'] }" /></span>		
			</div>
			
			<div class="form-group">
				<label for="amountBill">Montant</label>
				<input type="number" class="form-control" id="amountBill"
				 name="amountBill" value="<c:out value="${ bill.amount }"/>" placeholder="Montant" aria-describedby="amountBill2">
				<span id="amountBill2" class="help-block"><c:out value="${ form.erreurs['amountBill'] }" /></span>		
			</div>
			
			
			<input class="btn btn-default" type="submit" value="Créer">		
			<input class="btn btn-default" type="reset" value="Réinitialiser">
			
			<p class="info">${form.resultat}</p>
		
		</form>
	
	</div>
	
	
		


	
	<c:import url="/inc/footer.jsp"></c:import>