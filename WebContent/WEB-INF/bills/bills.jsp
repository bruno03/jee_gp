<%@ page 
pageEncoding="UTF-8" 
language="java"
contentType="text/html; 
charset=UTF-8"
%>
	

	<c:import url="/inc/header.jsp"></c:import>
	<c:import url="/inc/menu.jsp"></c:import>

	<div class="container">
	
		<h1>Liste des Factures</h1>
		
		<c:choose>
		
			<c:when test="${ empty bills }">
				<p>Pas de factures</p>
			</c:when>
			
			<c:otherwise>
				<table class="table table-bordered">
					<tr>
						<th>#</th>
						<th>Date</th>
						<th>Client ID</th>
						<th>Voiture ID</th>
						<th>KM</th>
						<th>Montant</th>
					</tr>
						
					<c:forEach items="${ bills }" var="bill" varStatus="boucle">                          
						<tr>
							<td>
								<a href="<c:url value="/facture">
											<c:param name="billId" value="${ bill.id }" />
										</c:url>"> 		
							<c:out value="${ bill.id }" />
							 </a>    		
							</td>
							<td><c:out value="${ bill.date }" /></td>
							<td><c:out value="${ bill.client.fullName }" /></td>
							<td><c:out value="${ bill.car.fullName }" /></td>
							<td><c:out value="${ bill.km }" /></td>
							<td><c:out value="${ bill.amount }" /></td>
						</tr>				
					</c:forEach>			
				</table>	
						
			</c:otherwise>
			
		</c:choose>

	</div>
	
	
	<c:import url="/inc/footer.jsp"></c:import>