function downloadGuide(url){	
	if(validationDownloadGuideFormInfo()){
		alert("please input right infomation!");
		return false;
	}
	else{
		downloadFile(url);
		hideErrowMessage();
		alert("download success!");
	}
}

function validationDownloadGuideFormInfo(){	
	var inval=false;
	$(".down-load-guide-form").each(function(){
		if(/required/.test($(this).attr("class"))){
			if(!$(this).val()||$(this).val()==""){
				var fieldName="error_for_"+$(this).attr("id");
				$("#"+fieldName).show();
				inval=true;
			}
		}		
	});
	return inval;
}

function hideErrowMessage(){
	$("div[id^=error_for_]").each(function(){$(this).hide();});
}

function downloadFile(url) {   
    try{ 
        var elemIF = document.createElement("iframe");   
        elemIF.src = url;   
        elemIF.style.display = "none";   
        document.body.appendChild(elemIF);   
    }catch(e){ 
    	 window.confirm("download failed!");
    } 
}