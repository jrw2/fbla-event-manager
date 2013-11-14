function showDiv(divName){
	
	if ($("#" + divName).is(":visible")) {
		$("#" + divName).slideUp("slow");
    } else {
    	$(".addEntryDiv").slideUp("slow");
        $("#" + divName).slideToggle("slow");
    }

}


