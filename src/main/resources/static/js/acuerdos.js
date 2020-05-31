function listarInstituciones(id) {
	(document.getElementsByName("actividad")).forEach(function(item) {
		if (item.checked) {
			console.log(item.checked + " " + item.value);
		}
	});
}

function peticion(id) {

	$.ajax({
		url : '/api/acuerdo/buscaractividad/' + id,
		type : 'GET',
		success : function(data) {

			var dataJSON = JSON.parse(data);

			console.log(dataJSON);

			$("#nombre").html(dataJSON.nombre);
			$("#responsable").html(dataJSON.responsable);
			$("#contacto").html(dataJSON.contacto);
			$("#unidades").html(dataJSON.unidades);
			$("#areaUnidad").html(dataJSON.areaUnidad);
			$("#secretarias").html(dataJSON.secretarias);
			$("#areaSecretaria").html(dataJSON.areaSecretaria);
			$("#tipos").html(dataJSON.tipos);
			$("#tematicas").html(dataJSON.tematicas);
			$("#ods").html(dataJSON.ods);
			$("#descripcion").html(dataJSON.descripcion);
			window.location = "#modal";
		},
		error : function(data) {
		}
	});
}

function peticionCUITNombre() {

	var cuit = document.getElementById("cuit").value;
	var nombre = document.getElementById("nombre").value;

	$.ajax({

		url : '/api/acuerdo/buscaractividadempresas/' + cuit + '&' + nombre,
		type : 'GET',
		success : function(data) {
			var dataJSON = JSON.parse([ data ]);
			
			$('li[name ="list"]').remove();
			
			for (var i = 0; i < dataJSON.length; i++) {
				$("#actividades").prepend(
						"<li name='list'>" + dataJSON[i].nombre + "</li>");
			}
		},
		error : function(data) {
		}
	});
}

function formatFechas(fecha) {
	var newFecha = "";
	for (var i = 0; i < fecha.length; i++) {
		if (fecha[i] == "-") {
			break;
		}
		newFecha += fecha[i];
	}
	return newFecha;
}