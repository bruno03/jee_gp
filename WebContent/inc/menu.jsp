<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
	<nav class="navbar navbar-default">
  <div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Garage des Planches</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a href="<c:url value="/clients"/>">Clients <span class="sr-only">(current)</span></a></li>
        <li><a href="<c:url value="/voitures"/>">Voitures</a></li> 
        <li><a href="<c:url value="/factures"/>">Factures</a></li> 
        <li><a href="<c:url value="/newBill"/>">Nouvelle factures</a></li>     
        <li><a href="<c:url value="/categories"/>">Categories d'article</a></li>     
        <li><a href="<c:url value="/articles"/>">Inventaire d'article</a></li>     
        
      </ul>
 
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
	