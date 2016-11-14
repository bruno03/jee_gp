<%@ page 
pageEncoding="UTF-8" 
language="java"
contentType="text/html; 
charset=UTF-8"
%>
	

	<c:import url="/inc/header.jsp"></c:import>
	<c:import url="/inc/menu.jsp"></c:import>

	<div class="container">
	
		<h1>Liste des Voitures</h1>
		
		<c:choose>
		
			<c:when test="${ empty cars }">
				<p>Pas de voitures</p>
			</c:when>
			
			<c:otherwise>
				<table class="table table-bordered">
					<tr>
						<th>#</th>
						<th>Marque</th>
						<th>Modèle</th>
						<th>Plaque immatriculation</th>
						<th>Client_id</th>
						<th>Client</th>
					</tr>
						
					<c:forEach items="${ cars }" var="car" varStatus="boucle">                          
						<tr>
							<td>
								<a href="<c:url value="/voiture">
											<c:param name="carId" value="${ car.id }" />
										</c:url>"> 		
							<c:out value="${ car.id }" />
							 </a>    		
							</td>
							<td><c:out value="${ car.brand }" /></td>
							<td><c:out value="${ car.model }" /></td>
							<td><c:out value="${ car.immatriculation }" /></td>
							<td><c:out value="${ car.clientId }" /></td>
							<td><c:out value="${ car.client.fullName }" /></td>
						</tr>				
					</c:forEach>			
				</table>	
						
			</c:otherwise>
			
		</c:choose>

	</div>
	
	
	<c:import url="/inc/footer.jsp"></c:import>