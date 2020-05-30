$(function() {

	$('#buscar').on("click", function(e) {
		var de = $('#desde').val();
		var ha = $('#hasta').val();

		var al = $('#alumno').val();
		if(al == null || al == 'null'){
			al = '';
		}
		
		var cu = $('#curso').val();

		window.location = '/inscripcion/listado?q=' + de + ',' + ha + ',' + al + ',' + cu;

	});
	
	$("#alumno").select2({
	    minimumInputLength: 3,
	    ajax: { // instead of writing the function to execute the request we use Select2's convenient helper
	        url: '/api/alumno/listar',
	        dataType: 'json',
	        quietMillis: 250,
	        processResults: function (resultado, page) {
	        	var data = {results: []};
		        for (var i = 0; i < resultado.length; i++) {
	        	    data.results.push({id: resultado[i].id,  text: resultado[i].nombreCompleto});
	        	}
	        	
	            return data;
	        },
	        cache: true
	    },
	    escapeMarkup: function (m) { return m; }
	}).on("select2-selecting", function(e) { 
		
    	alert("selecting val="+ e.val+" choice="+ JSON.stringify(e.choice));
    });

	$('#curso').select2();
	
	$('#formapago').select2();
	
	$('#formapago').on("change", function(e) { 
	
			var fm = $('#formapago').val();
			var cu =  $('.curso').val();
			
			$.ajax({
		        url: '/api/formapago/simular',
		        data: 'idFormaPago=' + fm + '&idCurso=' + cu,
		        dataType: "json",
		        async: true,
		        success: function (resultado) {
		        	$("#cuerpo").empty();
		        	var montoFT = 0;
		        	var interesFT = 0;
		        	
		        	for (var i = 0; i < resultado.cuotas.length; i++) {
		        		montoFT += resultado.cuotas[i].monto;
		        		interesFT += resultado.cuotas[i].interes; 
		        		
		        		$("#cuerpo").append("<tr><td>" + resultado.cuotas[i].numero 
		        							  + "</td> <td style=\"text-align:center;\">" + resultado.cuotas[i].fechaVencimiento 
		        							  + "</td> <td>" + resultado.cuotas[i].descripcion 
		        							  + "</td> <td style=\"text-align:right;\"> $ " + resultado.cuotas[i].monto.toFixed(2)
		        							  + "</td> <td style=\"text-align:right;\"> $ " + resultado.cuotas[i].interes.toFixed(2)
		        							  + "</td> <td style=\"text-align:right;\"> $ " + (resultado.cuotas[i].monto + resultado.cuotas[i].interes).toFixed(2)  + "</td></tr>");
		        	}
		        	
		        	$("#montoFT").empty();
		        	$("#montoFT").append(montoFT.toFixed(2));
		        	
		        	$("#interesFT").empty();
		        	$("#interesFT").append(interesFT.toFixed(2));
		        	
		        	$("#totalFT").empty();
		        	$("#totalFT").append((montoFT + interesFT).toFixed(2));
		        	
		        }
		    });
		});
	
	$('.curso').on("change", function(e) {
		var idCurso = $(".curso").val();
		armarCombo(idCurso,"turno", '/api/turno/combo?idC=' + idCurso, "Seleccione un turno", "curso");
	});
	
});