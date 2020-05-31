function asignarDato(dato, id) {
	document.getElementById(id).value = document.getElementById(id).value
			.concat(dato, "/");
}

function asignarImagen(imagen, dato) {
	document.getElementById(dato).value = document.getElementById(dato).value
			.concat(imagen, "/");
}

function vaciarCampo(dato) {
	document.getElementById(dato).value = null;
}

function asignarCheck(name, id) {
	var array = document.getElementsByName(name);
	var txt = "";
	var i;
	for (i = 0; i < array.length; i++) {
		if (array[i].checked) {
			txt = txt + array[i].value + "/";
		}
	}
}

function asignarInput(name, id) {
	var array = document.getElementsByName(name);
	var txt = "";
	var i;

	for (i = 0; i < array.length; i++) {

		if (array[i] !== "") {
			txt = txt + array[i].value + "/";
		}
	}
}
