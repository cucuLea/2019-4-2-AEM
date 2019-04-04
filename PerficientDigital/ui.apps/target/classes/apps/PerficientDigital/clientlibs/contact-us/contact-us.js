function sendEmail(){
			
	if(validationFormInfo()){
		alert("please input right infomation!");
		return false;
	}
	else{
		hideErrowMessage();
		var form = $("#emailInformationForm").serialize();
           $.ajax({
                url:"/bin/perficientDigital/emailServlet",   
                type:"post",
                data:form,
                success:function(data){
	  				  alert("success!");
                    console.log("over..");
                },
                error:function(e){           
                	alert("errow");
                }
            }); 
		
	}                  
       }

function validationFormInfo(){	
	var inval=false;
	$(".contact-us-form").each(function(){
		if($(this).attr("required")=="required"){
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