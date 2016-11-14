<%@ page 
pageEncoding="UTF-8" 
language="java"
contentType="text/html; 
charset=UTF-8"
%>
	

	<c:import url="/inc/header.jsp"></c:import>
	<c:import url="/inc/menu.jsp"></c:import>

	<div class="container">
	
		<h1>Client N° <c:out value="${ client.id }" /></h1>
		
		<p> </p>
		
		<dl class="dl-horizontal">
  			<dt>Nom</dt>
  			<dd><c:out value="${ client.lastname }" /></dd>
  			
  			<dt>Prénom</dt>
  			<dd><c:out value="${ client.firstname }" /></dd>
  			
  			<dt>Adresse</dt>
  			<dd><c:out value="${ client.addresse }" /></dd>
  			
  			<dt>CP</dt>
  			<dd><c:out value="${ client.cp }" /></dd>
  			
  			<dt>Ville</dt>
  			<dd><c:out value="${ client.city }" /></dd>
		</dl>
		
		<a class="btn btn-default" href="<c:url value="/updateClient">
									<c:param name="clientId" value="${ client.id }" />
								</c:url>" role="button">Modifier les données clients</a>
		
		<!-- Button trigger modal -->
		<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#myModal">
		  Supprimer le client
		</button>
		
		<!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="myModalLabel">Suppression du client</h4>
		      </div>
		      <div class="modal-body">
		        <p>Vous allez supprimer le client suivant : </p>
		        <p><c:out value="${ client.fullName }" /></p>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
		        <a class="btn btn-danger" href="<c:url value="/deleteClient">
							<c:param name="clientId" value="${ client.id }" />
						</c:url>" role="button">Supprimer</a>	
		      </div>
		    </div>
		  </div>
		</div>

								
		<br>
		<br>
		
		
		<h3>Liste des voitures du client</h3>
		
		<a class="btn btn-default" href="<c:url value="/newCar">
											<c:param name="clientId" value="${ client.id }" />
										</c:url>" role="button">Ajouter une voiture</a>
		<br/>
		<br/>
		
		<c:choose>
	
			<c:when test="${ empty client.cars }">
				<p>Pas de voitures</p>
			</c:when>
			
			<c:otherwise>	
				<table class="table table-stripped">
					<tr>
						<th>ID</th>			
						<th>Marque</th>
						<th>Modèle</th>	
						<th>Immatriculation</th>						
					</tr>
								
					<c:forEach items="${ client.cars }" var="car" varStatus="boucle">                          
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
						</tr>				
					</c:forEach>		
				</table>	
			</c:otherwise>
		
		</c:choose>	
		
		
				
	</div>
	
	
	<c:import url="/inc/footer.jsp"></c:import>