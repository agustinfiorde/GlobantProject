$('#agregar').on("click", function(e) { 
	var data = $('#materia').select2('data');
	var costo = $('#costo').val();
	var electiva = $('#electiva:checked').val();
	var msjElectiva = "";
	if(!electiva){
		msjElectiva = '<span><i class="fa fa-times"></i></span>';
		electiva = false;
	}else{
		msjElectiva = '<span><i class="fa fa-check"></i></span>';
	}
	var idCurso = $('#id').val();
	if(data != null){
		var fila = $('#tabla tr').length;

		$("#cuerpo").append('<tr id="fila' + fila +'">' 
				+ '<td>' + data[0].text + '</td>'
				+ '<td>' + costo + '</td>'
				+ '<td>' + msjElectiva + '</td>'
				+ '<td style="text-align:right" id="input' + fila + '"><a href="#" onclick="eliminar('+ fila +');return false;"><i class="icono_accion fa fa-trash"></i></a>'
				+ '<input type="hidden" name="curriculasModel[' + fila + '].materiaModel.id" value="' + data[0].id + '"/>'
				+ '<input type="hidden" name="curriculasModel[' + fila + '].costo" value="' + costo + '"/>'
				+ '<input type="hidden" name="curriculasModel[' + fila + '].cursoModel.id" value="' + idCurso + '"/>'
				+ '<input type="hidden" name="curriculasModel[' + fila + '].electiva" value="' + electiva + '"/>'
				+ '</td></tr>' );
	}
	
	$("#materia").val(null).trigger('change');
	$('#costo').val("");
	$('#electiva').val(true);

});

function eliminar(id){
	var row = parseInt(id) - parseInt(1);
	$("#fila" + id).hide();
	$("#input" + id).append('<input type="hidden" name="curriculasModel[' + row  + '].baja" value="' + new Date() + '"/>');
}
