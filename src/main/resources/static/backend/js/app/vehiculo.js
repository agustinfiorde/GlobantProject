$(function() {

	$('#marca').on("change", function(e) {
		var id = $("#marca").val();
		armarCombo(id,"modelo", '/api/cotizador/modelos?id=' + id, "Seleccione un modelo", "marca");
	});
	

	$('#modelo').on("change", function(e) {
		vehiculo();
	});
	
	$('#anio').on("change", function(e) {
		vehiculo();
	});

	$('#compania').on("change", function(e) {
		var id = $("#compania").val();
		armarCombo(id,"cobertura", '/api/cotizador/cobertura?idCompania=' + id, "Seleccione una cobertura", "compania");
	});
	
	$('#cobertura').on("change", function(e) {
		var id = $("#compania").val();
		var idCobertura = $("#cobertura").val();
		var idZona = $("#zona").val();
		var idTipoVehiculo = $("#tipoId").val();
		
		armarCombo(id,"vigencia", '/api/cotizador/vigencia?idCompania=' + id, "Seleccione una vigencia", "compania");
		
		$.ajax({
			url : '/api/cotizador/tarifa?idCobertura=' + idCobertura + '&idTipo=' + idTipoVehiculo + '&idZona=' + idZona,	
			type : 'GET',
			success : function(data) {
				$("#premio").val(data.precio);
			}
		});
	});
	
	function vehiculo(){
		var idModelo = $("#modelo").val();
		var anio = $("#anio").val();
		
		$.ajax({
			url : '/api/cotizador/vehiculo?idModelo=' + idModelo + '&anio=' + anio,	
			type : 'GET',
			success : function(data) {
				$("#vehiculo").val(data.id);
				$("#precio").val(data.monto);
				$("#tipo").val(data.modelo.tipo.nombre);
				$("#tipoId").val(data.modelo.tipo.id);
			}
		});
		
	}
	
	$('#vigencia').on("change", function(e) {

		var idVigencia = $("#vigencia").val();
		var premio = $("#premio").val();
		
		$.ajax({
			url : '/api/cotizador/cuotas?idVigencia=' + idVigencia + '&premio=' + premio,	
			type : 'GET',
			success : function(data) {
	        	for (var i = 0; i < data.length; i++) {

	        		$("#cuerpo").append("<tr><td>" + data[i].cuota 
						  + "</td> <td style=\"text-align:center;\">" + data[i].vencimiento 
						  + "</td> <td>" + data[i].detalle 
						  + "</td> <td style=\"text-align:right;\"> $ " + data[i].monto.toFixed(2)
						  + "</td>");
	        	}
			}
		});
		
	});

});