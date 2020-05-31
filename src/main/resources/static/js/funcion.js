function carga() {

	$("#areaOrganizacion option[value != '']").remove();
	var organizaciones = new Array();
	var count = 0;

	for (var i = 0; i < document.getElementsByClassName("orgSelect").length; i++) {
		if (document.getElementsByClassName("orgSelect")[i].selected) {
			organizaciones[count] = document.getElementsByClassName("orgSelect")[i].value;
			count++;
		}
	}

	for (var i = 0; i < organizaciones.length; i++) {
		$.ajax({
			url : '/api/areaorganizacion/buscarareas/' + organizaciones[i],
			type : 'GET',
			success : function(data) {
				var dataJSON = JSON.parse(data);

				for (var i = 0; i < dataJSON.length; i++) {
					$("#areaOrganizacion").append(new Option(dataJSON[i].nombre, dataJSON[i].id));
				}
			},
			error : function(data) {
			}
		});

	}
}
