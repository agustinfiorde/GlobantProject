
var locations = [ [ 'Bondi Beach', -36.33743774784831, -69.19845732653062, 11],
		[ 'Coogee Beach', -33.00243136506813, -69.38021753061224, 10],
		[ 'Cronulla Beach', -35.352641014022765, -66.98863589795918, 9],
		[ 'Manly Beach', -36.33743774734831, -66.988635839795918, 8],
		[ 'Maroubra Beach', -36.337433774784831, -66.988635893795918, 7],
		[ 'Manly Beach', -36.337433774784831, -66.98863589795918, 6],
		[ 'Maroubra Beach', -36.33743774784831, -66.988363589795918, 5],
		[ 'Manly Beach', -36.337433774784831, -66.988363589795918, 4],
		[ 'Maroubra Beach', -36.333743774784831, -66.988633589795918, 3],
		[ 'Manly Beach', -36.337437743784831, -66.988633589795918, 2],
		[ 'Maroubra Beach', -36.333743774784831, -66.988635897395918, 1] ];

function initMap() {

	var map = new google.maps.Map(document.getElementById('map'), {
		zoom : 7.2,
		center : {
			lat : -34.795428,
			lng : -68.485766
		}
	});
	var infowindow = new google.maps.InfoWindow();

	var marker, i;
	var markers = [];

	for (i = 0; i < locations.length; i++) {
		marker = new google.maps.Marker(
				{
					position : new google.maps.LatLng(locations[i][1],
							locations[i][2]),
					map : map
				});

		markers.push(marker);

		google.maps.event.addListener(marker, 'click', (function(marker, i) {
			return function() {
				infowindow.setContent(locations[i][0]);
				infowindow.open(map, marker);
			}
		})(marker, i));
	}

	console.log(markers[0]);
}
