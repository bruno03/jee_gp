<%@ page 
pageEncoding="UTF-8" 
language="java"
contentType="text/html; 
charset=UTF-8"
%>
	

	<c:import url="/inc/header.jsp"></c:import>
	<c:import url="/inc/menu.jsp"></c:import>

	<div class="container">
	
		<h1>Liste des clients</h1>
		
		<a class="btn btn-default" href="<c:url value="/newClient"></c:url>" role="button">Nouveau client</a>
		<br/>

		<br/>	
		<input id="search" type="text" class="form-control" placeholder="Rechercher un client">						
		<br/>
		
		<c:choose>
		
			<c:when test="${ empty clients }">
				<p>Pas de client</p>
			</c:when>
			
			<c:otherwise>
				<table class="table table-bordered">
					<tr>
						<th>#</th>
						<th>Nom</th>
						<th>Prenom</th>
						<th>Adresse</th>
						<th>CP</th>
						<th>Ville</th>
					</tr>
						
					<c:forEach items="${ clients }" var="client" varStatus="boucle">                          
						<tr>
							<td>
								<a href="<c:url value="/client">
											<c:param name="clientId" value="${ client.id }" />
										</c:url>"> 		
							<c:out value="${ client.id }" />
							 </a>    		
							</td>
							<td><c:out value="${ client.lastname }" /></td>
							<td><c:out value="${ client.firstname }" /></td>
							<td><c:out value="${ client.addresse }" /></td>
							<td><c:out value="${ client.cp }" /></td>
							<td><c:out value="${ client.city }" /></td>
						</tr>				
					</c:forEach>		
						
				</table>				
			</c:otherwise>			
		</c:choose>				
	</div>
		
	
	<c:import url="/inc/footer.jsp"></c:import>