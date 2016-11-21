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
  			
  			<dt>Client</dt>
  			<dd><c:out value="${ bill.client.fullName }" /></dd>
  			
  			<dt>Voiture</dt>
  			<dd><c:out value="${ bill.car.fullName }" /></dd>
  			
  			<dt>KM</dt>
  			<dd><c:out value="${ bill.km }" /></dd>
  			
  			<dt>Montant </dt>
  			<dd><c:out value="${ bill.amount }" /></dd>
		</dl>
								
		<br>
		
		<table class="table table-stripped">
			<tr>
				<th>ID</th>
				<th>Quantité</th>
				<th>Description</th>
				<th>Prix unitaire</th>
				<th>Prix final</th>
				<th>FactureID</th>
			</tr>
			
			<c:forEach items="${ bill.details }" var="detail" varStatus="boucle">                          
						<tr>
							<td><c:out value="${ detail.id }" /></td>
							<td><c:out value="${ detail.quantity }" /></td>
							<td><c:out value="${ detail.description }" /></td>
							<td><c:out value="${ detail.unitAmount }" /></td>
							<td><c:out value="${ detail.finalAmount }" /></td>
							<td><c:out value="${ detail.billId }" /></td>
							
						</tr>				
					</c:forEach>		
		</table>
		
		
		
		<br>
		
		<form class="form-inline" method="post" action="newDetailBill">
  			<div class="form-group">
    			<input type="text" class="form-control" id="quantity" name="quantity" placeholder="Quantité">
    		</div>
  			<div class="form-group">
    			<input type="text" class="form-control" id="description" name="description" placeholder="Description">
  			</div>
  			
  			<div class="form-group">
    			<input type="text" class="form-control" id=unityAmount name="unitAmount" placeholder="Montant unitaire">
  			</div>
  			
  			<div class="form-group">
    			<input type="text" class="form-control" id="finalAmount" name="finalAmount" placeholder="Montant final">
  			</div>
  			
  			<div class="form-group">
    			<input type="hidden" class="form-control" id="billId" name="billId" placeholder="Facture ID" value="<c:out value="${ bill.id }"/>">
  			</div>
  			
  			<button type="submit" class="btn btn-default">Créer</button>
		</form>
				
	</div>
	
	
	<c:import url="/inc/footer.jsp"></c:import>