var creado=false;
var marcador;
var latlng;

function initMap() {
	var map = new google.maps.Map(document.getElementById('map'), {
		zoom : 7.2,
		center : {
			lat : -34.795428,
			lng : -68.485766
		}
	});
	var geocoder = new google.maps.Geocoder();
// geo = geocoder;

	document.getElementById('submit').addEventListener('click', function() {
		geocodeAddress(geocoder, map);
	});
	
    map.addListener('click', function(e) {
        placeMarkerAndPanTo(e.latLng, map);
        geocodeLatLng(geocoder);
      });  
}

function geocodeLatLng(geocoder) {

	latlng={lat : parseFloat(marcador.getPosition().lat()), lng: parseFloat(marcador.getPosition().lng())};
    getAddress(latlng,geocoder);
      }

function getAddress(latLng,geocoder) {
    geocoder.geocode( {'latLng': latLng},
      function(results, status) {
        if(status == google.maps.GeocoderStatus.OK) {
          if(results[0]) {
            document.getElementById("address").value = results[0].formatted_address;
          }
          else {
            document.getElementById("address").value = "No results";
          }
        }
        else {
          document.getElementById("address").value = status;
        }
      });
    }
  

function placeMarkerAndPanTo(latLng, map) {
	
	if (!creado) {
		var marker = new google.maps.Marker({
			map : map,
			animation : google.maps.Animation.DROP,
			title : document.getElementById("tituloActividad").value,
			position : latLng
		});

		creado=true;
		marcador=marker;
		
		latlng={lat : parseFloat(marcador.getPosition().lat()), lng: parseFloat(marcador.getPosition().lng())};
		
		document.getElementById("lat").value=String(latlng.lat);
		document.getElementById("lng").value=String(latlng.lng);
		
		console.log("Direccion en String "+document.getElementById("lat").value+" , "+String(latlng.lng));
	} else {
		marcador.setMap(map);
		marcador.setAnimation(google.maps.Animation.DROP);
		marcador.setTitle(document.getElementById("tituloActividad").value,);
		marcador.setPosition(latLng);
		
		document.getElementById("lat").value=String(latlng.lat);
		document.getElementById("lng").value=String(latlng.lng);
		
		console.log("Direccion en String "+document.getElementById("lat").value+" , "+String(latlng.lng));
	}
	map.panTo(latLng);
  }

function geocodeAddress(geocoder, resultsMap) {
	var address = document.getElementById('address').value;
	geocoder.geocode({
		'address' : address
	}, function(results, status) {
		if (status === 'OK') {
			var titulo = "Paradero de Waldir cuando sale de joda";
			resultsMap.setCenter(results[0].geometry.location);
			resultsMap.setZoom(18);
			if (!creado) {
				var marker = new google.maps.Marker({
					map : resultsMap,
					animation : google.maps.Animation.DROP,
					title : document.getElementById("tituloActividad").value,
					position : results[0].geometry.location
				});
				creado=true;
				marcador=marker;

				latlng={lat : parseFloat(marcador.getPosition().lat()), lng: parseFloat(marcador.getPosition().lng())};
				
				document.getElementById("lat").value=String(latlng.lat);
				document.getElementById("lng").value=String(latlng.lng);
				
				console.log("Direccion en String "+document.getElementById("lat").value+" , "+String(latlng.lng));

			} else {
				marcador.setMap(resultsMap);
				marcador.setAnimation(google.maps.Animation.DROP);
				marcador.setTitle(document.getElementById("tituloActividad").value,);
				marcador.setPosition(results[0].geometry.location);
				
				latlng={lat : parseFloat(marcador.getPosition().lat()), lng: parseFloat(marcador.getPosition().lng())};

				document.getElementById("lat").value=String(latlng.lat);
				document.getElementById("lng").value=String(latlng.lng);
				
				console.log("Direccion en String "+document.getElementById("lat").value+" , "+String(latlng.lng));
			}
		} else {
			alert('La ubicacion solicitada no existe: '
					+ status);
		}
	});
}

function convertLatLngtoString(latlng){ 
	return String(latlng.lat)+","+String(latlng.lng);
}
