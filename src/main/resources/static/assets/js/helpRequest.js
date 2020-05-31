function sendHelpRequest(){
	
	var helpRequestModel = {
			address: $("#address").val(),
			factTimeString:  new Date(),
			typesOfViolences: $("#aggressionTypes").val(),
			relationship: $("#relationShip").val(),
			description: $("#description").val(),
			name: $("#name").val(),
			lastName: $("#lastname").val(),
			dateBornString: $("#dateBorn").val(),
		 	dni: $("#dni").val(),
		 	phone: $("#phone").val(),
			email: $("#email").val(),
			children: $("#children").val(),
			whistleblower: true,
			ipAddress: null,
			nameAggressor: $("#nameAggressor").val(),
			secondNameAggressor: $("#middleNameAggressor").val(),
			lastNameAggressor: $("#lastNameAggressor").val(),
			dniAggressor: $("#dniAggressor").val()
	}
	console.log(helpRequestModel);
	
	$.ajax({
		url: '/api/helprequest/save',
		dataType : 'json', 
		contentType : 'application/json', 
		type : 'POST',
		data : JSON.stringify(helpRequestModel),
		success : function(data) { 
			$("#modalAlerta").empty().html('<div class="alert alert-success" role="alert">'
			  + 'Se envio el pedido de ayuada con exito!'
			  + '</div>').show();
			setTimeout(function() {
				$("#modalAlerta").hide();
			}, 5000);
		},
		error : function(data){
			$("#modalAlerta").empty().html('<div class="alert alert-danger" role="alert">'
			  + 'Ocurrio un error al crear el pedido, Completa todos los campos para porde enviar el pedido de ayuda.'
			  + '</div>').show();
			setTimeout(function() {
				$("#modalAlerta").hide();
			}, 5000);
		}
	});
	
}

function buildSelects(){
	$.ajax({
		url: '/api/helprequest/searchaggressiontypes',
		dataType : 'json', 
		contentType : 'application/json', 
		type : 'GET', 
		success : function(data) { 
			$("#select1").empty();
			if (data != null) {
				var select1 =  '<label style="margin-top: 10px;">Tipo de maltrato</label>'
							+ '<select class="form-control" id="aggressionTypes" multiple>'
							+ '<option disabled="disabled" selected="selected" hidden="">Seleccione un tipo de agresíon</option>';
					for (var i = 0; i < data.length; i++) {
						select1 += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
					}
				select1 += '</select>';
				$("#select1").append(select1);
			}
		} 
	});
	$.ajax({
		url: '/api/helprequest/searchrelationship',
		dataType : 'json', 
		contentType : 'application/json', 
		type : 'GET', 
		success : function(data) { 
			$("#select2").empty();
			if (data != null) {
				var select2 = '<label style="margin-top: 10px;">Tipo de relacion</label>'
							+ '<select class="form-control" id="relationShip">'
							+ '<option disabled="disabled" selected="selected" hidden="">Seleccione un tipo de relacíon</option>';
				for (var i = 0; i < data.length; i++) {
					select2 += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
				}
				select2 += '</select>';
				$("#select2").append(select2);
			}
		} 
	});
}
