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
			drawTimeLine(result);
		}
	});
}


function drawTimeLine(data) {
	
	var events = new Array[data.lenght];
	
	var i = 0;
	for (event in data) {
		events[i] = computeEvent(event);
		i++;
	}
	
	
	
	var timeline1 = new Chronoline(document
			.getElementById("target1"), events, {
		animated : true,
		tooltips : true,
		defaultStartDate : new Date(2012, 3, 5),
		sections : sections,
		sectionLabelAttrs : {
			'fill' : '#997e3d',
			'font-weight' : 'bold'
		},
		draggable : true,
	});
	
}

function computeEvent(event) {
	// {"period":{"startDate":"2013/09/10","endDate":"2013/09/12"},"title":"terrible-toadstool-16 [$ 6]"}
	var event = 
}


//parse a date in yyyy-mm-dd format
function parseDate(inputDate) {
  var parts = inputDate.split('/');
  // new Date(year, month [, date [, hours[, minutes[, seconds[, ms]]]]])
  return new Date(parts[0], parts[1]-1, parts[2]); // months are 0-based
}

function oldCode() {
	$(document)
	.ready(
			function() {

				var events = [
						{
							dates : [ new Date(2011, 2, 31) ],
							title : "2011 Season Opener",
							section : 0
						},
						{
							dates : [ new Date(2012, 1, 29) ],
							title : "Spring Training Begins",
							section : 1
						},
						{
							dates : [ new Date(2012, 3, 5) ],
							title : "Atlanta Braves @ New York Mets Game 1",
							section : 0
						},
						{
							dates : [ new Date(2012, 3, 7) ],
							title : "Atlanta Braves @ New York Mets Game 2",
							section : 1
						},
						{
							dates : [ new Date(2012, 3, 8) ],
							title : "Atlanta Braves @ New York Mets Game 3",
							section : 1
						},
						{
							dates : [ new Date(2012, 3, 9),
									new Date(2012, 3, 11) ],
							title : "Atlanta Braves @ Houston Astros",
							section : 0
						},
						{
							dates : [ new Date(2012, 3, 13),
									new Date(2012, 3, 15) ],
							title : "Milwaukee Brewers @ Atlanta Braves",
							section : 1
						},
						{
							dates : [ new Date(2012, 3, 13),
									new Date(2012, 3, 15) ],
							title : "Baltimore Orioles @ Toronto Blue Jays",
							section : 0
						},
						{
							dates : [ new Date(2012, 3, 17),
									new Date(2012, 3, 19) ],
							title : "Tampa Bay Rays @ Toronto Blue Jays",
							section : 1
						},
						{
							dates : [ new Date(2012, 3, 20),
									new Date(2012, 3, 23) ],
							title : "Toronto Blue Jays @ Kansas City Royals",
							section : 0
						},
						{
							dates : [ new Date(2012, 3, 5) ],
							title : "Opening Day for 12 Teams",
							section : 1
						},
						{
							dates : [ new Date(2012, 2, 28) ],
							title : "Seattle Mariners v. Oakland A's",
							section : 0,
							description : "Played in Japan!"
						},
						{
							dates : [ new Date(2012, 4, 18),
									new Date(2012, 5, 24) ],
							title : "Interleague Play",
							section : 1
						}, {
							dates : [ new Date(2012, 5, 10) ],
							title : "All-Star Game",
							section : 1
						}, {
							dates : [ new Date(2012, 9, 24) ],
							title : "World Series Begins",
							section : 0
						} ];

				var sections = [
						{
							dates : [ new Date(2012, 1, 1),
									new Date(2012, 9, 3) ],
							title : "Section 1",
							section : 0,
							attrs : {
								fill : "#dddddd"
							}
						},
						{
							dates : [ new Date(2012, 2, 28),
									new Date(2012, 9, 3) ],
							title : "Section 2",
							section : 1,
							attrs : {
								fill : "#d4e3fd"
							}
						} ];

				var timeline1 = new Chronoline(document
						.getElementById("target1"), events, {
					animated : true,
					tooltips : true,
					defaultStartDate : new Date(2012, 3, 5),
					sections : sections,
					sectionLabelAttrs : {
						'fill' : '#997e3d',
						'font-weight' : 'bold'
					},
					draggable : true,
				});

				$('#to-today').click(function() {
					timeline1.goToToday();
				});
			});

}