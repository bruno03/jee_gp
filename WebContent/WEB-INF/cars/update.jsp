<%@ page 
pageEncoding="UTF-8" 
language="java"
contentType="text/html; 
charset=UTF-8"
%>
	

	<c:import url="/inc/header.jsp"></c:import>
	<c:import url="/inc/menu.jsp"></c:import>

	<div class="container">
	
	<h1>Modifier les données de la voiture : </h1>
		<form method="post" action="updateCar">	
					
			<div class="form-group">
				<label for="brandCar">Marque</label>
				<input type="text" class="form-control" id="brandCar" 
				name="brandCar" placeholder="Marque" value="<c:out value="${ car.brand }"/>" aria-describedby="brandCar2">
				<span id="brandCar2" class="help-block"><c:out value="${ form.erreurs['brandCar'] }" /></span>
			</div>
			
			<div class="form-group">
				<label for="modelCar">Modèle</label>
				<input type="text" class="form-control" id="modelCar" 
				name="modelCar" value="<c:out value="${ car.model }"/>" placeholder="Modèle" aria-describedby="modelCar2">
				<span id="modelCar2" class="help-block"><c:out value="${ form.erreurs['modelCar'] }" /></span>
			</div>
			
			<div class="form-group">
				<label for="immatriculationCar">Immatriculation</label>
				<input type="text" class="form-control" id="immatriculationCar"
				 name="immatriculationCar" value="<c:out value="${ car.immatriculation }"/>" placeholder="Immatriculation" aria-describedby="immatriculationCar2">
				<span id="immatriculationCar2" class="help-block"><c:out value="${ form.erreurs['immatriculationCar'] }" /></span>		
			</div>
			
			<div class="form-group">
				<input type="hidden" class="form-control" id="clientIdCar" 
				name="clientIdCar" value="<c:out value="${ car.clientId }"/>" placeholder="" aria-describedby="clientIdCar2">
				<span id="clientIdCar2" class="help-block"><c:out value="${ form.erreurs['clientIdCar'] }" /></span>		
			</div>
			
			<div class="form-group">
				<input type="hidden" class="form-control" id="carId" 
				name="carId" value="<c:out value="${ car.id }"/>" placeholder="" aria-describedby="carId2">
				<span id="carId2" class="help-block"><c:out value="${ form.erreurs['carId'] }" /></span>		
			</div>
			
			<input class="btn btn-default" type="submit" value="Mettre à jour">
			
			<p class="info">${form.resultat}</p>
		
		</form>
	
	</div>
	
	
	<c:import url="/inc/footer.jsp"></c:import>