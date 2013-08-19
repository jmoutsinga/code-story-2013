/*
 * Timeglider for Javascript / jQuery 
 * http://timeglider.com/jquery
 *
 * Copyright 2012, Mnemograph LLC
 * Licensed under Timeglider Dual License
 * http://timeglider.com/jquery/?p=license
 *
 */


(function(tg){

  var $ = jQuery;
      

  /*
  *  @constructor
  */
  tg.TG_OrgList = function(events, med) {
  
  	var me = this;
    var icon_f = tg.icon_folder;

    this.blocks = events;
    this.ids = [];
    
    this.mediator = med;
       
   
	/*
	* ******** PUBLIC METHODS **********
	*/
  
    


    /*
    * TG_OrgList.getHTML
    * no args: just straight list of all events in a timeline...
    *
    * @return {string} HTML with events passed back to view for actual layout of timeline
    */
    this.getHTML = function () {
    
		var positioned = [], 
			span_selector_class, 
			span_div, 
			img = '', 
			icon = '',
			html = '', 
			more = '',
			desc = '',
			datef = '',
			elip = '',
			b = {},
			stripdesc = '',
			blength = this.blocks.length,

			img_scale = 100,
			css_class = "",
			p_icon = "";
		
	
		for (var i=0; i<blength; i++) {
		
	  		b = this.blocks[i];

			img_scale = 100;
			img_style = "";

			if (b.image) {
			
				// different image classes ("bar", "above") are positioned
				// using a separate $.each routine in TimelineView rather than
				// being given absolute positioning here.
				img = "<div class='tg-event-list-img'><img src='" + b.image.src + "' " + img_style + "></div>";
				
				
			} else {
				// no image
				img = "";
			} 
				
			b_span_color = (b.span_color) ? ";background-color:" + b.span_color: "";
		
			b.fontsize < 10 ? b.opacity = b.fontsize / 10 : b.opacity=1;
		
			if (b.span == true) {
				span_selector_class = "timeglider-event-spanning";
				// add seconds into span data in case calculations
				// are in demand in DOM
				
			} else {
				span_selector_class = ""; 
				
			}

			if (b.icon) {
			  icon_img = "<img src='" + icon_f + b.icon + "'>";
			} else {
			  icon_img = '';
			}
			
			icon = "<div class='tg-event-list-icon'>" + icon_img + "&nbsp;</div>"
			
			// possible customized class
			css_class = b.css_class || '';
			
			if (b.description || img) {
				more = "<img src='timeglider/img/mobile_more.png'>";
			} else {
				more = "";
			}
			
			if (b.description) {
				// strip tags to we have no half baked html in blurb
				stripdesc = b.description.replace(/(<([^>]+)>)/ig,"");
				elip = (stripdesc.length>110) ? "...":"";
				desc = "<div class='tg-list-blurb'>" + stripdesc.substr(0,110) + elip + "</div>";
			} else {
				desc = "";
			}
			
			
			datef = b.startdateObj.format('', true, this.mediator.timeOffset);
		 
			// TODO: function for getting "standard" event shit
			html += "<li class='tg-list-li timeglider-timeline-event clearfix" 
				+ css_class + " " + span_selector_class 
				+ "' id='" + b.id + "'>"
				+ "<div class='tg-list-dateline timeglider-dateline-startdate'>" + datef + "</div>"
				+ img + icon 
				+ "<div class='timeglider-event-title'>" 
				+ b.title
				
				+ "</div>"
				+ desc + "</li>";
							

			
		} // end for()

	
	return {"html":html};


	}; /// end getHTML


 
 
}; ///// END TG_OrgList
      
      
	
})(timeglider);	