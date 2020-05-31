function sendHelpRequest(){
	
	var helpRequest = {
			address: $("#name").val(),
			factTimeString:  new Date(),
			typesOfViolences: $("#aggressionTypes").val(),
			relationship: $("#relationShip").val(),
			description: $("#description").val()
	}
	var victim = {
			name: $("#name").val(),
			lastName: $("#lastname").val(),
			dateBornString: $("#dateBorn").val(),
		 	dni: $("#dni").val(),
		 	phone: $("#phone").val(),
			email: $("#email").val(),
			children: $("#children").val(),
			whistleblower: $("#whistleblower").val(),
			ipAddress: null
	}
	var aggressor = {
			name: $("#nameAggressor").val(),
			secondName: $("#middleNameAggressor").val(),
			lastName: $("#lastNameAggressor").val(),
			dni: $("#dniAggressor").val()
	}
	
	console.log(helpRequest);
	console.log(victim);
	console.log(aggressor);
	
	$.ajax({
		url: '/api/helprequest/save',
		beforeSend : function(request) { 
			request.setRequestHeader("helpRequest", helpRequest); 
			request.setRequestHeader("victim", victim); 
			request.setRequestHeader("aggressor", aggressor);
		}, 
		dataType : 'json', 
		contentType : 'application/json', 
		type : 'POST', 
		success : function(data) { 
			console.log(data);
		},
		error : function(data){
			console.log(data);
		}
	});
	
}

function buildSelects(){
	$.ajax({
		url: '/api/helprequest/serchrelationship',
		dataType : 'json', 
		contentType : 'application/json', 
		type : 'GET', 
		success : function(data) { 
			var select1 = '<select class="form-control my-lg-3" id="relationShip">'
						+ '<option disabled="disabled" selected="selected" hidden="">Seleccione un tipo de relacíon</option>';
			if (data != null) {
				for (var i = 0; i < data.length; i++) {
					select1 += '<opction value="' + data[i].id + '">' + data[i].name + '</option>'
				}
			}
			select1 += '</select>';
			$("#select1").append(select1);
		} 
	});
	$.ajax({
		url: '/serchaggressionTypes',
		dataType : 'json', 
		contentType : 'application/json', 
		type : 'GET', 
		success : function(data) { 
			var select2 = '<select class="selectpicker my-lg-3" id="aggressionTypes" multiple>'
						+ '<option disabled="disabled" selected="selected" hidden="">Seleccione un tipo de agresíon</option>';
			if (data != null) {
				for (var i = 0; i < data.length; i++) {
					select2 += '<opction value="' + data[i].id + '">' + data[i].name + '</option>';
				}
			}
			select2 += '</select>';
			$("#select2").append(select1);
		} 
	});
}
