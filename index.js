var geocoder;
var map;

function load () {
	geocoder = new google.maps.Geocoder();
	var latlng = new google.maps.LatLng(-34.397, 150.644);
	var myOptions = {
		zoom: 8,
		center: latlng,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	map = new google.maps.Map(document.getElementById("map"), myOptions);
}



function validate (type, obj) {
	if (obj.value == "") {
		$("#"+obj.id).removeClass("valid");
		$("#"+obj.id).addClass("invalid");
	} else {
		$("#"+obj.id).addClass("valid");
		$("#"+obj.id).removeClass("invalid");
	}
}

function show(lat,lng) {
	var latlng = new google.maps.LatLng(lat, lng);
	map.setCenter(latlng);
	geocoder.geocode({'latLng': latlng}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			if (results[0]) {
				map.setZoom(13);
				document.getElementById("from").value=results[0].formatted_address;
			}
		} else {
			alert("Geocoder failed due to: " + status);
		}
	});
}

function successLocation (location) {
	obj=document.getElementById("from");
	
	obj.value=location.coords.latitude+" "+location.coords.longitude;
	show (location.coords.latitude,location.coords.longitude);
	validate ("location", obj);
}

function geoIP () {
	obj=document.getElementById("from");
	obj.value=geoip_city()+", "+geoip_region_name()+", "+geoip_country_name();
	show (geoip_latitude(),geoip_longitude());
	validate ("location", obj);
}

function showLocation () {
	geoIP();
	if (navigator.geolocation) {
	  navigator.geolocation.getCurrentPosition(successLocation);
	} else if (google.gears) {
		var geo = google.gears.factory.create('beta.geolocation');
		geo.getCurrentPosition(successLocation);
	}
	
	return false;
}