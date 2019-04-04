package com.aem.community.core;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "PerficientDigital-EmailSendMember",description = "PerficientDigital-EmailSendMember Service Configuration, send email to somebody")
public @interface EmailReceiveMemberConfig {	
		@AttributeDefinition(
		        name = "Email address",
		        description = "email will send to these address",
		        type = AttributeType.STRING
		    )
		String[] getEmailAddress();
	}