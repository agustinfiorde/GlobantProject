function modificar(id){
	var orden = $('[name="preguntasModel[' + id + '].orden"]').val();
	var tipo = $('[name="preguntasModel[' + id + '].tipo"]').val();
	
	var pregunta = $('[name="preguntasModel[' + id + '].titulo"]').val();
	var respuestas = $('[name="preguntasModel[' + id + '].respuestas"]').val();

	var obligatoria = $('[name="preguntasModel[' + id + '].obligatoria"]').val();
	
	$("#orden").val(orden);
	$("#tipo").val(tipo);
	$("#pregunta").val(pregunta);
	
	$("#obligatoria").val(obligatoria);

	seleccionar();
	$("#respuesta").tagsinput('add', respuestas);

	eliminar(id);
}

function eliminar(id){
	$("#fila" + id).remove();
}

function seleccionar(){
	var tipo = $("#tipo").val();
	if(tipo == 'CERRADA'){
		$("#abierta").css("display", "inherit");
	} else {
		$("#abierta").css("display", "none");
	}
}

$(function() {
	
	$('#orden').val($('#tabla tr').length);
	
	
	$('#agregar').on("click", function(e) { 

		var orden = $('#orden').val();
		
		var idTipo = $('#tipo').val();
		var tipo = $("#tipo option:selected").text();

		var idObligatoria = $('#obligatoria').val();
		var obligatoria = $("#obligatoria option:selected").text();

		var respuestas = $('#respuesta').val();
		var pregunta = $("#pregunta").val();

		
		if(tipo != null){
			var fila = $('#tabla tr').length;
	
			$("#cuerpo").append('<tr id="fila' + fila +'"><td><input type="hidden" name="preguntasModel[' + fila + '].orden" value="' + orden + '"/>' + orden + '</td><td><input type="hidden" name="preguntasModel[' + fila + '].titulo" value="' + pregunta + '"/>' + pregunta + '</td><td><input type="hidden" name="preguntasModel[' + fila + '].tipo" value="' + idTipo + '"/>' + tipo + '</td><td><input type="hidden" name="preguntasModel[' + fila + '].obligatoria" value="' + idObligatoria + '"/>' + obligatoria + '</td><td><input type="hidden" name="preguntasModel[' + fila + '].respuestas" value="' + respuestas + '"/>' + respuestas + '</td><td style="text-align:right"><a href="#" onclick="eliminar('+ fila +');return false;"><i class="icono_accion fa fa-trash"></i></a></td></tr>' );
		}
		
		$('#orden').val(fila + 1);
		
		$('#tipo').val('');

		$('#obligatoria').val('');

		$('#respuesta').val('');
		$("#pregunta").val('');

	});	
});