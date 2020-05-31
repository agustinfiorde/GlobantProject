
var mesG = ''; 
var anioG = ''; 

function render(){
	var moment = $('#calendar').fullCalendar('getDate');
	
	var mes = moment.format('M');
	if(mes < 10){
		mes = '0' + mes; 
	}
	
	var anio = moment.format('Y');
	
	if(mesG != mes || anioG != anio){
		mesG = mes;
		anioG = anio; 
		refrescar();
	}
}

function refrescar(){
	var moment = $('#calendar').fullCalendar('getDate');
	
	var mes = moment.format('M');
	if(mes < 10){
		mes = '0' + mes; 
	}
	
	var dia = moment.format('D');
	if(dia < mes){
		dia = '0' + dia;
	}
	
	var anio = moment.format('Y');

	var dato = mes + '/' + anio;
	
	$.ajax({
        url: '/api/calendario/visualizar',
        data: $('#formulario').serialize() + '&mes=' + dato,
        dataType: "json",
        async: true,
        success: function (resultado) {
            $('#calendar').fullCalendar('removeEvents');
            $('#calendar').fullCalendar('addEventSource', resultado);         
            $('#calendar').fullCalendar('rerenderEvents' );
        }
    });
}

 
 $(function () {
	 
	$('#visualizar').click(function(){
		refrescar();
	}); 

	
	$('#calendar').fullCalendar({
	      header    : {
	        left  : 'prev,next today',
	        center: 'title',
	        right : 'month,agendaWeek,agendaDay'
	      },
	      buttonText: {
	        today: 'Hoy',
	        month: 'Mensual',
	        week : 'Semanal',
	        day  : 'Diario'
	      },
	      locale: 'es',
	      events    : [],
	      eventClick: function(event) {
	    	  window.open(event.url, event.title, 'width=700,height=600');
	    	  return false;
	      },
          viewRender: function (view, element) {
        	  render();
          }

	});
	
  })

      
      