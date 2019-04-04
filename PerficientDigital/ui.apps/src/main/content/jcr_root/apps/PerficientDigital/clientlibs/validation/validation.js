
(function(window, document, $, Granite, undefined) {
    "use strict";
    var registry = $(window).adaptTo("foundation-registry");

    registry.register("foundation.validation.validator", {
    selector: "[data-validation=phone-number]",
    validate: function (element) {
		var value = element.value;
        var reg=/^\(\d{3}\)\d{3}\-\d{4}$/;
        if(reg.test(value)){
			return false;
        }
        else{
            return "please input phone with right format";
        }
    }
       
    });


    registry.register("foundation.validation.validator", {
    selector: "[data-validation=internal-link-url]",
    validate: function (element) {
		var value = element.value;
        var reg=/^\/content.+/;
        if(reg.test(value)){
			return false;
        }
        else{
            return "just internal url is permitted";
        }
    }
       
    });

})(window, document, Granite.$, Granite);

