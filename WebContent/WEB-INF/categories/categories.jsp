<%@ page 
pageEncoding="UTF-8" 
language="java"
contentType="text/html; 
charset=UTF-8"
%>
	

	<c:import url="/inc/header.jsp"></c:import>
	<c:import url="/inc/menu.jsp"></c:import>

	<div class="container">
	
		<h1>Liste des catégories d'article</h1>
			
		<c:choose>
		
			<c:when test="${ empty categories }">
				<p>Pas de categories</p>
			</c:when>
			
			<c:otherwise>
				<table class="table table-bordered">
					<tr>
						<th>#</th>
						<th>Nom</th>
						
					</tr>
						
					<c:forEach items="${ categories }" var="cat" varStatus="boucle">                          
						<tr>
							<td><c:out value="${ cat.id }" />  		</td>
							<td><c:out value="${ cat.name }" /></td>

						</tr>				
					</c:forEach>							
				</table>				
			</c:otherwise>			
		</c:choose>	
		
		
		
		<br>
		<br>
		<form class="form-inline" method="post" action="categories">
		
			<div class="form-group">
				<input type="text" class="form-control" id="nomCat" name="nameCat" placeholder="Nom" 
				value="<c:out value="${ cat.name }"/>" aria-describedby="nameCat2">
				<span id="nameCat2" class="help-block"><c:out value="${ form.erreurs['nameCat'] }" /></span>
			</div>
			
			<button type="submit" class="btn btn-default">Créer</button>
		</form>			
	</div>
		
	
	<c:import url="/inc/footer.jsp"></c:import>