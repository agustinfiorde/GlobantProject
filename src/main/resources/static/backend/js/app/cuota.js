$(function() {

	$('#buscar').on("click", function(e) {
		var de = $('#desde').val();
		var ha = $('#hasta').val();

		var al = $('#alumno').val();
		if(al == null || al == 'null'){
			al = '';
		}

		var co = $('#codigo').val();
		if(co != null && co != ''){
			window.location = '/cuota/listado?codigo=' + co;
		} else {
			window.location = '/cuota/listado?q=' + de + ',' + ha + ',' + al;
		}

	});

	$('#buscarimpagas').on("click", function(e) {
		var de = $('#desde').val();
		var ha = $('#hasta').val();

		var al = $('#alumno').val();
		if(al == null || al == 'null'){
			al = '';
		}

		var co = $('#codigo').val();
		if(co != null && co != ''){
			window.location = '/cuota/listado?codigo=' + co;
		} else {
			window.location = '/cuota/impagas?q=' + de + ',' + ha + ',' + al;
		}

	});

	
	$("#alumno").select2({
	    minimumInputLength: 3,
	    ajax: { 
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

});