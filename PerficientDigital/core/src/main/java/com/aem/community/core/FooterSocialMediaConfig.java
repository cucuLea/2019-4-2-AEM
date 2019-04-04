package com.aem.community.core;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;


@ObjectClassDefinition(name = "PerficientDigital-FooterSocialMedia",description = "PerficientDigital-FooterSocialMedia Service Configuration")
public @interface FooterSocialMediaConfig {	
		@AttributeDefinition(
		        name = "Social Media CSS style and link",
		        description = "input with such format: name;Media CSS style;link; (ex: 'youtube;style;www.youtube.com')",
		        type = AttributeType.STRING
		    )
		    String[] getSocialMediaInfoList();		
		
	}