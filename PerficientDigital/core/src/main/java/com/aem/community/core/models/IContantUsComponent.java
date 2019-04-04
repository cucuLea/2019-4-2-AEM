package com.aem.community.core.models;

import java.util.List;

import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


@Model(adaptables=Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface IContantUsComponent {
	
	@ValueMapValue 
	String getTitle();
	
	@ValueMapValue
	String getPhoneNumber();
	
	@ValueMapValue
	String getReceiveEmailsText();
	
	@ValueMapValue  @Named("words")
	String getCaption();    
	
	@ValueMapValue 
	String getFirstName();

	@ValueMapValue 
	String getLastName();
	
	@ValueMapValue 
	String getWorkEmail();
		
	@ChildResource 
    List<AdditionalFormItem> getAdditionalFormItems();

}

