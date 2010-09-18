var geocoder;
var map;
var time=null;

function load () {
	geocoder = new google.maps.Geocoder();
	var latlng = new google.maps.LatLng(-34.397, 150.644);
	var myOptions = {
		zoom: 8,
		center: latlng,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	map = new google.maps.Map(document.getElementById("map"), myOptions);
	
	geoIP();
}

function checkAddress (obj) {
var address = obj.value;
	geocoder.geocode( { 'address': address}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			$("#"+obj.id).removeClass("invalid");
			$("#"+obj.id).addClass("valid");
		} else {
			$("#"+obj.id).removeClass("valid");
			$("#"+obj.id).addClass("invalid");
		}
	});
}

function validate (type, obj) {
	if (obj.value == "") {
		$("#"+obj.id).removeClass("valid");
		$("#"+obj.id).addClass("invalid");
	} else {
		if (time!=null) {
			clearTimeout (time);
		}
		time = setTimeout (function(){
			checkAddress(obj);
			time=null;
		},500);
	}
}

function show(lat,lng,level) {
	var latlng = new google.maps.LatLng(lat, lng);
	map.setCenter(latlng);
	geocoder.geocode({'latLng': latlng}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			if (results[level]) {
				map.setZoom(13);
				document.getElementById("from").value=results[level].formatted_address;
			}
		} else {
			alert("Geocoder failed due to: " + status);
		}
	});
}

function successLocation (location) {
	obj=document.getElementById("from");
	
	obj.value=location.coords.latitude+" "+location.coords.longitude;
	show (location.coords.latitude,location.coords.longitude,0);
	validate ("location", obj);
}

function geoIP () {
	obj=document.getElementById("from");
	obj.value=geoip_city()+", "+geoip_region_name()+", "+geoip_country_name();
	show (geoip_latitude(),geoip_longitude(),2);
	validate ("location", obj);
}

function showLocation () {
	if (navigator.geolocation) {
	  navigator.geolocation.getCurrentPosition(successLocation);
	} else if (google.gears) {
		var geo = google.gears.factory.create('beta.geolocation');
		geo.getCurrentPosition(successLocation);
	}
	
	return false;
}