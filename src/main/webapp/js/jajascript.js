
/**
 * Responsible to put content of the loaded file in the textarea.
 */
function readSingleFile(evt) {
	// Retrieve the first (and only!) File from the FileList object
	var f = evt.target.files[0];

	if (f) {
		var reader = new FileReader();
		reader.onload = function(e) {
			var contents = e.target.result;
			document.getElementById('fileContent').innerHTML = contents;
		};
		reader.readAsText(f);
	} else {
		var msg = "Failed to load file";
		console.log(msg);
		alert(msg);
	}
}

/**
 * Call the server and adapt returned data to chronoline.js format
 */
function doOptimize() {
	var myForm = $('#form2');
	$.ajax({
		type : myForm.attr('method'),
		url : myForm.attr('action'),
		data : myForm.serialize(),
		success : function(result) {
			console.log(result);
			drawTimeLine(result);
		}
	});
}

function drawTimeLine(data) {

}

//parse a date in yyyy-mm-dd format
function parseDate(inputDate) {
  var parts = inputDate.split('/');
  // new Date(year, month [, date [, hours[, minutes[, seconds[, ms]]]]])
  return new Date(parts[0], parts[1]-1, parts[2]); // months are 0-based
}