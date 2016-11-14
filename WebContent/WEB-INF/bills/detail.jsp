<%@ page 
pageEncoding="UTF-8" 
language="java"
contentType="text/html; 
charset=UTF-8"
%>
	

	<c:import url="/inc/header.jsp"></c:import>
	<c:import url="/inc/menu.jsp"></c:import>

	<div class="container">
	
		<h1>Facture N° <c:out value="${ bill.id }" /></h1>
		
		<p> </p>
		
		<dl class="dl-horizontal">
  			<dt>Date</dt>
  			<dd><c:out value="${ bill.date }" /></dd>
  			
  			<dt>Client ID</dt>
  			<dd><c:out value="${ bill.clientId }" /></dd>
  			
  			<dt>Voiture ID</dt>
  			<dd><c:out value="${ bill.carId }" /></dd>
  			
  			<dt>KM</dt>
  			<dd><c:out value="${ bill.km }" /></dd>
  			
  			<dt>Montant </dt>
  			<dd><c:out value="${ bill.amount }" /></dd>
		</dl>
				
	</div>
	
	
	<c:import url="/inc/footer.jsp"></c:import>