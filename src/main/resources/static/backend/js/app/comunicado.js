$(function() {

	$("#destinatario").select2({
	    minimumInputLength: 3,
	    ajax: { 
	    	url: '/api/comunicado/destinatarios',
	        dataType: 'json',
	        quietMillis: 250,
	        multiple: true, 
	        processResults: function (resultado, page) {
	        	var data = {results: []};
		        for (var i = 0; i < resultado.length; i++) {
	        	    data.results.push({id: resultado[i].identificador,  text: resultado[i].nombre});
	        	}
	        	
	            return data;
	        },
	        cache: true
	    },
	    escapeMarkup: function (m) { return m; }
	});
	
	$('.mailbox-messages input[type="checkbox"]').iCheck({
	      checkboxClass: 'icheckbox_flat-blue',
	      radioClass: 'iradio_flat-blue'
	    });

    $(".checkbox-toggle").click(function () {
      var clicks = $(this).data('clicks');
      if (clicks) {
        $(".mailbox-messages input[type='checkbox']").iCheck("uncheck");
        $(".fa", this).removeClass("fa-check-square-o").addClass('fa-square-o');
      } else {
        $(".mailbox-messages input[type='checkbox']").iCheck("check");
        $(".fa", this).removeClass("fa-square-o").addClass('fa-check-square-o');
      }
      $(this).data("clicks", !clicks);
    });
	    
    $("#eliminar").click(function () {
    	alert("ELIMINAR");
    	var valores = [];
        $('#seleccionado :checked').each(function() {
          allVals.push($(this).val());
          alert("Seleccionado... " + $(this).val());
        });
    });
	
});