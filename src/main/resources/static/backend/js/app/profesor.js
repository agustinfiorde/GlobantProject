$('#agregar').on("click", function(e) { 
	var materia = $('#materia').select2('data');
	var turno = $('#turno').select2('data');
	var curso = $('#curso').select2('data');
	if(materia != null && materia[0].id != '-1' && curso != null && curso[0].id != '-1'){
		var fila = $('#tabla tr').length;
		$("#cuerpo").append('<tr id="fila' + fila +'">' 
				+ '<td class="col-sm-3">' + turno[0].text + '</td>'
				+ '<td class="col-sm-3">' + curso[0].text + '</td>'
				+ '<td class="col-sm-3">' + materia[0].text + '</td>'
				+ '<td class="col-sm-3" style="text-align:right" id="input' + fila + '"><a href="#" onclick="eliminar('+ fila +');return false;"><i class="icono_accion fa fa-trash"></i></a>'
				+ '<input type="hidden" name="cargosModel[' + fila + '].materiaModel.id" value="' + materia[0].id + '"/>'
				+ '<input type="hidden" name="cargosModel[' + fila + '].turnoModel.id" value="' + turno[0].id + '"/>'
				+ '</td></tr>' );
	}else{
		alert("seleccione un curso y una materia para agregar");
	}
	
	$("#curso").val("-1").trigger('change');
	$("#materia").empty().prop("disabled", "disabled").trigger('change');
	$("#turno").empty().prop("disabled", "disabled").trigger('change');

});


$('#curso').on("change", function(e) {
	var idCurso = $("#curso").val();
	$.ajax({ 
    	url: '/api/materia/combo?idC=' + idCurso,
        dataType: 'json',
        type : 'GET',
        success: function (resultado) {
        	var opciones = '<option value="-1"  style="display:none;">' + "Ingrese Materia" + '</option>';
        	$.each(resultado, function(i, item) {
					opciones = opciones + '<option value="' + item.id + '">'+ item.nombre+'</option>';
			});
        	$("#materia").empty().removeAttr("disabled", "disabled");
        	$('#materia').append(opciones);
        	$.ajax({ 
            	url: '/api/turno/combo?idC=' + idCurso,
                dataType: 'json',
                type : 'GET',
                success: function (resultado) {
                	var opciones = '<option value="-1" style="display:none;">' + "Ingrese Turno" + '</option>';
                	$.each(resultado, function(i, item) {
        					opciones = opciones + '<option value="' + item.id + '">'+ item.nombre+'</option>';
        			});
                	$("#turno").empty().removeAttr("disabled", "disabled");
                	$('#turno').append(opciones);
                }
            });
        }
    });
});

function eliminar(id){
	var row = parseInt(id) - parseInt(1);
	var idCargo = $("#cargo" + row).val();
	if(idCargo != null && idCargo != ""){
		$("#fila" + id).hide();
		$("#input" + id).append('<input type="hidden" name="cargosModel[' + row  + '].baja" value="' + new Date() + '"/>');
	}else{
		$("#fila" + id).hide();
		$("#fila" + id).empty();
	}
}
