$('.time1').datetimepicker({
	        format: 'HH:mm',
	});
		
$('#agregar').on("click", function(e) { 
	var data = $('#dias').select2('data');
	var entrada = $('#entrada').val();
	var salida = $('#salida').val();
	$.each(data, function(i, obj) {
		if(data != null){
			var fila = $('#tabla tr').length;
	
			$("#cuerpo").append('<tr id="fila' + fila +'"><td>' 
					+ obj.id + '</td>'
					+ '<td>' + entrada + '</td><td>' + salida + '</td>'
					+ '<td style="text-align:right" id="input' + fila + '">'
					+ '<input type="hidden" name="horariosModel[' + fila + '].dia" value="' + obj.id + '"/>'
					+ '<input type="hidden" name="horariosModel[' + fila + '].entrada" value="' + entrada + '"/>'
					+ '<input type="hidden" name="horariosModel[' + fila + '].salida" value="' + salida+ '"/>'
					+ '<a href="#" onclick="eliminar('+ fila +');return false;"><i class="icono_accion fa fa-trash"></i></a></td></tr>' );
		}
	});
	
	$("#dias").val(null).trigger('change');
	$('#entrada').val("");
	$('#salida').val("");

});

function eliminar(id){
	var row = parseInt(id) - parseInt(1);
	var idHorario = $("#horario" + row).val();
	if(idHorario != null && idHorario != ""){
		$("#fila" + id).hide();
		$("#input" + id).append('<input type="hidden" name="horariosModel[' + row  + '].baja" value="' + new Date() + '"/>');
	}else{
		$("#fila" + id).hide();
		$("#fila" + id).empty();
	}
}