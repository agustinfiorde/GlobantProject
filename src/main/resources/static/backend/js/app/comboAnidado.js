
function armarCombo(idSeleccionado, select, url, mensajeOpcion, selectOrigen) {
	$.ajax({
		url : url,
		type : 'GET',
		success : function(data) {
		    	var isDisabled = $('.' + selectOrigen).prop('disabled');
		    	if(!isDisabled){
		    	    $('.' + select).removeAttr("disabled", "disabled");
		    	}
			$('#' + select).empty();
			var opciones = '';
			if(mensajeOpcion != null && mensajeOpcion != ""){
			    opciones = '<option value="-1" selected="selected" style="display:none;">' + mensajeOpcion + '</option>';
			}
			$.each(data, function(i, item) {
				if(idSeleccionado != null && idSeleccionado == item.id){
					opciones = opciones + '<option value="' + item.id + '" selected="selected">'+ item.nombre+'</option>';
					
				}else{
					opciones = opciones + '<option value="' + item.id + '">'+ item.nombre+'</option>';
				}
			      
			});
			
			$('#' + select).append(opciones);
			
		}
	});
}

function deshabilitarCombo(select, mensaje){
	$("." + select).empty()
	.prop('disabled', 'disabled')
	if(mensaje != null && mensaje != ""){
	    $("." + select).append('<option value="-1" selected="selected" disabled="disabled">' + mensaje + '</option>')
	}
}

	