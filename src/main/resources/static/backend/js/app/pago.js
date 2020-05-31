$(function() {

	$("#guardaraprobar").on("click", function(e){
		$("aprobar").val(true);
		$("formulario").submit();
	});
	
	
	$('#buscar').on("click", function(e) {
		var de = $('#desde').val();
		var ha = $('#hasta').val();

		var al = $('#alumno').val();
		if(al == null || al == 'null'){
			al = '';
		}
		
		window.location = '/pago/listado?q=' + de + ',' + ha + ',' + al;

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

	$('#alumno').on('select2:select', function (e) {
		   var id = $('#alumno').val();
			
			$.ajax({
		        url: '/api/alumno/inscripciones',
		        data: 'id=' + id,
		        dataType: "json",
		        async: true,
		        success: function (resultado) {
		        	$("#inscripcion").empty();
		        	for (var i = 0; i < resultado.length; i++) {
		        		var id = resultado[i].id;
		        		var nombre = resultado[i].turnoModel.cursoModel.nombre;
		        		
		        		$("#inscripcion").append('<option value="' + id + '">' + nombre + '</option>');
		        	
		        }
		        }
			});
	});
});