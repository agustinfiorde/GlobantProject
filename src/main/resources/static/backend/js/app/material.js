$('.curso').on("change", function(e) {
		var idCurso = $(".curso").val();
		armarCombo(idCurso,"materia", '/api/materia/combo?idC=' + idCurso, "Seleccione Materia", "curso");
	});
