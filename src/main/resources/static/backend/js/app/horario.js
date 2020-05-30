
$('.time1').datetimepicker({
	        format: 'HH:mm',
	});

$('#horarios').on("select2:select", function(e) {
	var select = $('#horarios').select2().val();
	var idHorario = select.split('~')[1];
	var horarios = select.split(' ');
	var dia = horarios[0];
	var entrada = horarios[1];
	var salida = horarios[3];
	
	$('#entrada').data("DateTimePicker").minDate(entrada).maxDate(salida);
	$('#salida').data("DateTimePicker").minDate(entrada).maxDate(salida);
	$('#entrada').val(entrada);
	$('#salida').val(salida);
	$("#dia").val(dia);
	$("#idHorario").val(idHorario);
});
		
$('#generar').on("click", function(e) { 
	var dia = $('#dia').val();
	var entrada = $('#entrada').val();
	var salida = $('#salida').val();
	var idHorario = $('#idHorario').val();
	var fila = $('#tabla tr').length;

	var isTrue = existe(dia, entrada, salida);
	
	if(!existe(dia,entrada,salida)){
		$("#cuerpo").append('<tr id="fila' + fila +'">' 
				+ '<td><input type="hidden" class="' + dia +'" name="horariosAlumnosModel[' + fila + '].dia" id="' + fila + '" value="' + dia + '"/>'
				+ '<input type="hidden" name="horariosAlumnosModel[' + fila + '].entrada" id="entrada-'+ fila +'" value="' + entrada + '"/>'
				+ '<input type="hidden" name="horariosAlumnosModel[' + fila + '].salida" id="salida-'+ fila +'" value="' + salida+ '"/>'
				+ '<input type="hidden" name="horariosAlumnosModel[' + fila + '].horarioCursoModel.id" value="' + idHorario+ '"/>'
				+ dia + '</td>'
				+ '<td>' + entrada + '</td><td>' + salida + '</td>'
				+ '<td style="text-align:right"><a href="#" onclick="eliminar('+ fila +');return false;"><i class="icono_accion fa fa-trash"></i></a></td></tr>' );
	}else{
		setTimeout(function () {
	        var elem = document.getElementById('error').innerHTML = "";
	    }, 5000);
	}
	
	$('#horarios').val(null).trigger('change');
	$('#entrada').val("");
	$('#salida').val("");
	$('#idHorario').val("");

});

function existe(dia,entrada,salida){
	
    var elementos = $('.' + dia);
    var size = elementos.size();
    var isTrue = false;
    $.each( elementos, function(i, val){
        var id = $(val).attr('id');
        var entradaAnterior = parseInt($("#entrada-" + id).val().replace(":", ""));
        var salidaAnterior = parseInt($("#salida-" + id).val().replace(":", ""));
        entrada = parseInt(entrada.replace(":", ""));
        salida = parseInt(salida.replace(":", ""));
        if((entrada >= entradaAnterior && entrada <= salidaAnterior) || (salida >= entradaAnterior && salida <= salidaAnterior)){
        	$("#error").empty();
        	$("#error").append('<div class="alert alert-danger" role="alert">'
						+ '<strong>¡Error!</strong> <span> El horario que intenta agregar ya existe</span>'
						+ '</div>');
        	isTrue = true;
        }
    });
    
    
    return isTrue;
    
}

function eliminar(id, dia){
	var row = parseInt(id) - parseInt(1);
	var idHorario = $("#horario" + row).val();
	if(idHorario != null && idHorario != ""){
		$("#fila" + id).hide();
		$("#input" + id).append('<input type="hidden" name="horariosAlumnosModel[' + row  + '].baja" value="' + new Date() + '"/>');
		var element = document.getElementById(id);
	    element.classList.remove(dia);
	}else{
		$("#fila" + id).hide();
		$("#fila" + id).empty();
	}
	
}