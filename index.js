
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

function showLocation () {
	obj=document.getElementById("from");
	obj.value="Sydney, Australia";
	validate ("location", obj);
	
	return false;
}