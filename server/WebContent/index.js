var time;
var markers = [];

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

function loadedAjax (data) {
	data = eval (data);
	var res = $("#res");
	
	for (var i=0;i<markers.length; i++) {
		markers[i].setMap(null);
	}
	markers = [];

	
	var table = $("<table cellspacing='0'><tbody><tr><td>Name</td><td>suburb</td><td>eta (City)</td><td>eta (Home)</td><td>rego</td><td></td></tr></tbody></table>");
	
	for (var i=0; i<data.length; i++) {
		var row = data[i];
		var tr = $("<tr onmouseover=\"this.className='highlight';markers["+i+"].setIcon('ok.gif');\" "+
				"onmouseout=\"this.className='';markers["+i+"].setIcon('bad.gif');\" "+
				"><td>"+row["contactName"]+"</td><td>"+row["suburb"]+"</td><td>"+row["etaCity"]+
				"</td><td>"+row["etaHome"]+"</td><td>"+row["rego"]+"</td><td><a href='javascript:;'>Contact Me</a></td><tr>");
				
		table.children().append (tr);
		
		markers[i] = new google.maps.Marker({
         position: new google.maps.LatLng(row["coords"][0],row["coords"][1]), 
         map: map
		});
		markers[i].setIcon("bad.gif");
	}
	
	res.empty();
	res.append (table);
	res.className = "show";
	$("#spinner").className = "hidden";
}

/*
function loadedAjax (data) {
	data = eval (data);
	var res = document.getElementById ("res");
	
	for (var i=0;i<markers.length; i++) {
		markers[i].setMap(null);
	}
	markers = [];

	
	var table = document.createElement ("table");
	
	table.innerHTML = "<tbody><tr><td>Name</td><td>suburb</td><td>eta (City)</td><td>eta (Home)</td><td>rego</td><td></td></tr></tbody>";
	for (var i=0; i<data.length; i++) {
		var row = data[i];
		var tr = document.createElement ("tr");
		tr.innerHTML = "<td>"+row["contactName"]+"</td><td>"+row["suburb"]+"</td><td>"+row["etaCity"]+
				"</td><td>"+row["etaHome"]+"</td><td>"+row["rego"]+"</td><td><a href='javascript:;'>Contact Me</a></td>";
		eval("tr.onmouseover = function(){this.className='highlight';markers["+i+"].setIcon('ok.gif');}");
		eval("tr.onmouseout = function(){this.className='';markers["+i+"].setIcon('bad.gif');}");
		table.childNodes[0].appendChild (tr);
		markers[i] = new google.maps.Marker({
         position: new google.maps.LatLng(row["coords"][0],row["coords"][1]), 
         map: map
		});
		markers[i].setIcon("bad.gif");
	}
	
	res.innerHTML = "";
	res.appendChild (table);
	res.className = "show";
	$("#spinner").className = "hidden";
}*/

function search (string) {
	if (string == "") {
		res.innerHTML = "";
		
		for (var i=0;i<markers.length; i++) {
			markers[i].setMap(null);
		}
		markers = [];
		
		return;
	}
	
	document.getElementById("res").className = "hidden";
	$("#spinner").className = "show";
	loadedAjax ("[{suburb:'Chatswood',etaCity:'08:30',etaHome:'17:00', contactName:'ren',rego:'ABC029',coords:[-33.8833,151.2167]},\
		{suburb:'Cabramatta',etaCity:'06:45',etaHome:'17:15', contactName:'Andrew',rego:'L0LC0D',coords:[-33.8853,151.2227]}]")
}


function checkAddress (obj) {
var address = obj.value;
	geocoder.geocode( { 'address': address}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			$("#"+obj.id).removeClass("invalid");
			$("#"+obj.id).addClass("valid");
			map.setCenter(results[0].geometry.location);
			if (!obj.marker) {
				obj.marker = new google.maps.Marker({
               position: results[0].geometry.location, 
               map: map
				});
			} else {
				obj.marker.setPosition(results[0].geometry.location);
			}
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