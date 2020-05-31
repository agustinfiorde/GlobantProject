function eliminar(id){
	$("#fila" + id).remove();
}

$(function() {

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

    });
	
	$('#agregar').on("click", function(e) { 
		var data = $('#alumno').select2('data');
		if(data != null){
			var fila = $('#tabla tr').length;
	
			$("#alumno").val(null).trigger('change');
			var alumno = $("." + data[0].id).val();
			if(alumno == null || alumno == ""){
				$("#cuerpo").append('<tr id="fila' + fila +'"><td>'
						+ '<input type="hidden" class="' + data[0].id + '" name="alumnosModel[' + fila + '].id" value="' + data[0].id + '"/>'  
						+ '<input type="hidden" name="alumnosModel[' + fila + '].nombreCompleto" value="' + data[0].text + '"/>' + data[0].text
						+ '</td>'
						+ '<td style="text-align:right"><a href="#" onclick="eliminar('+ fila +');return false;"><i class="icono_accion fa fa-trash"></i></a></td></tr>' );
			}
		}
	});
});