
function load () {
	var latlng = new google.maps.LatLng(-34.397, 150.644);
	var myOptions = {
		zoom: 8,
		center: latlng,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	var map = new google.maps.Map(document.getElementById("map"), myOptions);
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

function successLocation (location) {
	obj=document.getElementById("from");
	obj.value=location.coords.latitude+" "+location.coords.longitude;
	validate ("location", obj);
}

function geoIP () {
	obj=document.getElementById("from");
	obj.value=geoip_city()+", "+geoip_region_name()+", "+geoip_country_name();
	validate ("location", obj);
}

function showLocation () {
	geoIP();
	if (navigator.geolocation) {
	  navigator.geolocation.getCurrentPosition(successLocation);
	}
	
	return false;
}