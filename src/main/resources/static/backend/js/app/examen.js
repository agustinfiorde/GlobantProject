$(function() {

	$('#buscar').on("click", function(e) {
		var de = $('#desde').val();
		var ha = $('#hasta').val();

		var al = $('#profesor').val();
		if(al == null || al == 'null'){
			al = '';
		}
		
		var cu = $('#cursoB').val();
		if(cu == null || cu == 'null' || cu=='%'){
			cu = '';
		}

		var ma = $('#materia').val();
		if(ma == null || ma == 'null' || ma=='%'){
			ma = '';
		}

		
		window.location = '/examen/listado?q=' + de + ',' + ha + ',' + al + ',' + cu + "," + ma;

	});
	
	$("#profesor").select2({
	    minimumInputLength: 3,
	    ajax: { 
	        url: '/api/profesor/listar',
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
	
});

$('.curso').on("change", function(e) {
		var idCurso = $(".curso").val();
		armarCombo(idCurso,"turno", '/api/turno/combo?idC=' + idCurso, "Seleccione un turno", "curso");
		armarCombo(idCurso,"materia", '/api/materia/combo?idC=' + idCurso, "Seleccione una materia", "curso");
		deshabilitarCombo("profesor", "Seleccione un profesor");
	});

$('.materia').on("change", function(e) {
	var idMateria = $(".materia").val();
	var idTurno = $(".turno").val();
	armarCombo(idMateria,"profesor", '/api/profesor/combo?idT=' + idTurno+ "&idM=" +idMateria, "", "curso");
});


