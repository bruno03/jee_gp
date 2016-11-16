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
		
		<form class="form-inline" method="get" action="clients">
			<input type="text" class="form-control" id="searchValue" name="searchValue" placeholder="" >		
			<input class="btn btn-default" type="submit" value="Rechercher">
		</form>
		
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
	
	
	<script type="text/javascript">
	
	
	
	</script>
	
	
	<c:import url="/inc/footer.jsp"></c:import>