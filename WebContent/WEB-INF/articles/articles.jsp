<%@ page 
pageEncoding="UTF-8" 
language="java"
contentType="text/html; 
charset=UTF-8"
%>
	

	<c:import url="/inc/header.jsp"></c:import>
	<c:import url="/inc/menu.jsp"></c:import>

	<div class="container">
	
		<h1>Liste des articles</h1>
		
		<a class="btn btn-default" href="<c:url value="/newArticle"></c:url>" role="button">Nouvel article</a>
		<br/>
		
		<c:choose>
		
			<c:when test="${ empty articles }">
				<p>Pas d'articles</p>
			</c:when>
			
			<c:otherwise>
				<table class="table table-bordered">
					<tr>
						<th>#</th>
						<th>Description</th>
						<th>Montant unitaire</th>
						<th>Catégorie</th>
					</tr>
						
					<c:forEach items="${ articles }" var="article" varStatus="boucle">                          
						<tr>
							<td>
								<a href="<c:url value="/article">
											<c:param name="articleId" value="${ article.id }" />
										</c:url>"> 		
							<c:out value="${ article.id }" />
							 </a>    		
							</td>
							<td><c:out value="${ article.description }" /></td>
							<td><c:out value="${ article.unitAmount }" /></td>
							<td><c:out value="${ article.category.name }" /></td>
						</tr>				
					</c:forEach>		
						
				</table>				
			</c:otherwise>			
		</c:choose>				
	</div>
		
	
	<c:import url="/inc/footer.jsp"></c:import>