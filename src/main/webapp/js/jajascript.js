function fillFileContent() {

	// select the form
	var $form = $('#form1');

	var formData = new FormData();
	formData.append( 'sendFile', $( '#sendFile' )[0].files[0] );
	
	// implement the callback on submit
	$form.on('submit', function() {

		$.ajax({
			url : $(this).attr('action'),
			enctype: 'multipart/form-data',
			type : $(this).attr('method'),
			data : formData,
			success : completeHandler,
			error : errorHandler,
			//Options to tell JQuery not to process data or worry about content-type
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