$(function() {

	$('#buscar').on("click", function(e) {
		var de = $('#desde').val();
		var ha = $('#hasta').val();

		var al = $('#nombre').val();
	
		window.location = '/evento/listado?q=' + de + ',' + ha + ',' + al;

	});
	
});