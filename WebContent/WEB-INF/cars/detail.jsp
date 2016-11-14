<%@ page 
pageEncoding="UTF-8" 
language="java"
contentType="text/html; 
charset=UTF-8"
%>
	

	<c:import url="/inc/header.jsp"></c:import>
	<c:import url="/inc/menu.jsp"></c:import>

	<div class="container">
	
		<h1>Voiture N° <c:out value="${ car.id }" /></h1>
		
		<p> </p>
		
		<dl class="dl-horizontal">
  			<dt>Marque</dt>
  			<dd><c:out value="${ car.brand }" /></dd>
  			
  			<dt>Modèle</dt>
  			<dd><c:out value="${ car.model }" /></dd>
  			
  			<dt>Immatriculation</dt>
  			<dd><c:out value="${ car.immatriculation }" /></dd>
  			
  			<dt>Client ID</dt>
  			<dd><c:out value="${ car.clientId }" /></dd>
  			
  			<dt>Client </dt>
  			<dd><c:out value="${ car.client.fullName }" /></dd>
		</dl>
		
		
		
		
		<a class="btn btn-default" href="<c:url value="/newBill">
									<c:param name="carId" value="${ car.id }" />
								</c:url>" role="button">Ajouter une facture</a>
				
	</div>
	
	
	<c:import url="/inc/footer.jsp"></c:import>