<%@ page 
pageEncoding="UTF-8" 
language="java"
contentType="text/html; 
charset=UTF-8"
%>
	

	<c:import url="/inc/header.jsp"></c:import>
	<c:import url="/inc/menu.jsp"></c:import>

	<div class="container">
	
	<h1>Nouvel article : </h1>
		<form method="post" action="newArticle">	
			<div class="form-group">
				<label for="dateBill">Description</label>
				<input type="text" class="form-control" id="descriptionArticle" 
				name="descriptionArticle" placeholder="" value="<c:out value="${ article.description }"/>" aria-describedby="descriptionArticle2">
				<span id="descriptionArticle2" class="help-block"><c:out value="${ form.erreurs['descriptionArticle'] }" /></span>
			</div>
			
			<div class="form-group">
				<label for="unitAmountArticle">Montant unitaire</label>
				<input type="text" class="form-control" id="unitAmountArticle" 
				name="unitAmountArticle" value="<c:out value="${ article.unitAmount }"/>" placeholder="" aria-describedby="unitAmountArticle2">
				<span id="unitAmountArticle2" class="help-block"><c:out value="${ form.erreurs['unitAmountArticle'] }" /></span>
			</div>
			
			<div class="form-group">
				<label for="carIdBill">Catégories</label>
				<select class="form-control" id="categoryIdArticle" name="categoryIdArticle" aria-describedby="categoryIdArticle2">
					<option value="0">------</option>
					<c:forEach items="${ categories }" var="cat" varStatus="boucle"> 
						<option value="<c:out value="${ cat.id }" />">
							<c:out value="${ cat.name }" />
						</option>
					</c:forEach>		
				</select>
				<span id="categoryIdArticle2" class="help-block"><c:out value="${ form.erreurs['categoryIdArticle'] }" /></span>		
			</div>		
			
			
			<input class="btn btn-default" type="submit" value="Créer">		
			<input class="btn btn-default" type="reset" value="Réinitialiser">
			
			<p class="info">${form.resultat}</p>
		
		</form>
	
	</div>
	
	
		


	
	<c:import url="/inc/footer.jsp"></c:import>