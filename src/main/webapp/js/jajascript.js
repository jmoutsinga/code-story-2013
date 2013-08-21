function fillFileContent() {

	// select the form
	var $form = $('#form1');

	var formData = new FormData();
	formData.append('sendFile', $('#sendFile')[0].files[0]);

	// implement the callback on submit
	$form.on('submit', function() {

		$.ajax({
			url : $(this).attr('action'),
			enctype : 'multipart/form-data',
			type : $(this).attr('method'),
			data : formData,
			success : completeHandler,
			error : errorHandler,
			// Options to tell JQuery not to process data or worry about
			// content-type
			cache : false,
			contentType : false,
			processData : false

		});
		return false;
	});

	// submit the form
	$form.trigger('submit');

}

function completeHandler(data, status, jqXHR) {
	alert('completeHandler');
	alert(data);
}

function beforeSendHandler() {
	alert('beforeSendHandler');
}

function errorHandler(jqXHR, status, error) {
	alert('errorHandler');
	alert(status);
	alert(error);

}
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
		alert("Failed to load file");
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
		}
	});
}
