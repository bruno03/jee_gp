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
		    			var array = []; 
		    			var length = data.length; 
		    			
		    			for(var i = 0; i < length; i++){
		    				array.push({
		    					label: data[i].firstname + " " + data[i].lastname, 
		    					value: data[i].id
		    					});	
		    			}
		    			response(array);
		    		}
		    	});
	    	},
	    	minLength: 2,
	    	select:function(event, ui){
	    		//console.log(ui.item.value + " " + ui.item.label);
	    		window.location.href="client?clientId="+ ui.item.value; 
	    	}
	    });
	    
	    
	    
	    var clients ; 
	    var client; 
	    var cars; 
	    
	    //on rend invisible les 2 div pour sélectionner ou ajouter une voiture 
		$("#carIdGroup").hide(); 
		$("#clientNoCar").hide();   
		
	    $("#clientIdBill").autocomplete({
	    	source:function(request,response){
	    		var value = $("#clientIdBill").val(); 
	    		$.ajax({
		    		url: 'searchClient',
		    		type: 'GET',
		    		dataType: 'json',
		    		data: 'searchValue='+ value,
		    		success: function(data, statut){
		    			clients = data; 
		    			var array = []; 
		    			var length = data.length; 
		    			
		    			for(var i = 0; i < length; i++){
		    				array.push({
		    					label: data[i].firstname + " " + data[i].lastname, 
		    					value: data[i].id,
		    					index: i
		    					});	
		    			}
		    			response(array);
		    		}
		    	});
	    	},
	    	minLength: 2,
	    	select:function(event, ui){
	    		
	    		//on initialise le client sélectionné
	    		client = clients[ui.item.index];	  
	    		
	    		//on met un div informatif du client sélectionné
	    		$("#clientSelected").empty(); 
	    		$("#clientSelected").append("<p>"+client.lastname + " " + client.firstname+"</p>");
	    		$("#clientSelected").append("<p>"+client.addresse +"</p>");
	    		$("#clientSelected").append("<p>"+client.cp + " " + client.city+"</p>");
	    		$("#clientSelected").append("<br/>");


	    		//on initalise les voitures du clients sélectionné
	    		cars = client.cars; 
	    		
	    		//test si le client possède des voiture ou non
	    		if(cars.length > 0){
	    			//on rend visible le div pour sélectionner une voiture
	    			$("#carIdGroup").show(); 
	    			
	    			//on rend invisible le div pour informer l'utilisateur que le client n'a pas de voiture
	    			$("#clientNoCar").hide();   
	    			
	    			//on nettoie le select 
	    			$("#carIdBill").empty(); 	
	    			
	    			//on implémente les options du select avec les voitures du client
		    		$.each(cars, function(i, car){
		    			$("#carIdBill").append($('<option>',{
		    				value:car.id,
		    				text: car.brand + " " + car.model
		    			}))
		    		});
	    		}
	    		else{
	    			//on rend invisible le div pour sélectionner une voiture 
	    			$("#carIdGroup").hide(); 
	    			
	    			//on rend visible le div pour informer l'utilisateur que le client n'a pas de voiture
	    			$("#clientNoCar").show();   
	  			
	    			//on modifie le lien pour créer une nouvelle voiture pour le client
	    			$("#clientNoCar a").attr("href", "newCar?clientId="+ client.id)
	    		}	    		
	    	}
	    });
 
	    

	    
	});
	
	
	</script>
	
</body>
</html>