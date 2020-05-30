'use Strict'

function ValidateEqualityPassword(clave1, clave2) {
	password = document.getElementById(clave1).value;
	confirmPassword = document.getElementById(clave2).value;
	confirmPassword_container = document.getElementById(clave2);
	if (password != confirmPassword) {
		Red(confirmPassword_container);
		return false;
	} else {
		
		if (confirmPassword.length >= 8 && confirmPassword.length <= 12) {
			Green(confirmPassword_container);
			return true;
		} else {
			Red(confirmPassword_container);
			return false;
		}
	}
}
	
function PasswordLength(id) {
	 password = document.getElementById(id).value;
	 password_container = document.getElementById(id);
	if (password.length >= 8 && password.length <= 12) {
	   Green(password_container);
		return true;
	} else {
		Red(password_container);
		return false;
	}
}

function ValidateDNI() {
	dni = document.getElementById("dni").value;
	for (var i = 0; i < dni.length; i++) {

		if (dni[i] == "." || dni[i] == ",") {
			if (dni[i] == ".") {
				document.getElementById('dni').value = document
						.getElementById('dni').value.replace(".", "");
			} else {
				document.getElementById('dni').value = document
						.getElementById('dni').value.replace(",", "");
			}
		}
	}
	;
	DniLength("dni");
}

function DniLength() {
	let dni = document.getElementById("dni").value;
	let dni_container = document.getElementById("dni");
	if (dni.length >= 7 && dni.length <= 8) {
		Green(dni_container);
		return true;
	} else {
		Red(dni_container);
		return false;
	}
}

function Red(stringId) {
	let bad_color = "#ff6666";
	stringId.style.borderColor = bad_color;
	stringId.style.borderWidth = "medium";
}

function Green(stringId) {
	let good_color = null;
	stringId.style.borderColor = good_color;
	stringId.style.borderWidth = "thin";
}

function ValidateAll(clave1, clave2){

	if (PasswordLength(clave2) && PasswordLength(clave1) && DniLength() && ValidateEqualityPassword(clave1, clave2)) {

		document.getElementById("boton_submit").disabled= false;

	}else{
		
		document.getElementById("boton_submit").disabled = true;

	}
}