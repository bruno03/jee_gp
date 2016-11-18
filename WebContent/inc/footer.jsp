<!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    
    <script src="inc/js/jquery-ui.min.js"></script>
    <script src="inc/js/datepicker-fr.js"></script>
    
	
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" 
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous">
	</script>
	
	
	<script>
	$( function() {
	    //datepicker pour le champs Date de la facture
		$( "#dateBill" ).datepicker({
	    	showWeek: true,
	        firstDay: 1
	    });
	    
	    $( "#dateBill" ).datepicker( "option", "dateFormat", "dd/mm/yy" );	    
	    $( "#dateBill" ).datepicker( $.datepicker.regional[ "fr" ] ); 
	    
	    
	    //autocomplete du champs de recherche des clients
	    $("#search").autocomplete({
	    	source:function(request, response){
	    		var value = $("#search").val(); 
		    	$.ajax({
		    		url: 'searchClient',
		    		type: 'GET',
		    		dataType: 'json',
		    		data: 'searchValue='+ value,
		    		success: function(data, statut){
		    			//console.log(data);
		    			response(data);
		    		}
		    	});
	    	},
	    	minLength: 2,
	    	select:function(event, ui){
	    		//console.log(ui.item.value + " " + ui.item.label);
	    		window.location.href="client?clientId="+ ui.item.value; 
	    	}
	    });
	    
 
	    
	    
	    
	});
	
	
	</script>
	
</body>
</html>