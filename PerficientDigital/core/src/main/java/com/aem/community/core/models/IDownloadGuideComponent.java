package com.aem.community.core.models;

import java.util.List;

import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables=Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface IDownloadGuideComponent {

	@ValueMapValue 
	String getTitle();
	
	@ValueMapValue
	String getTextOfGetTheGuide();
	
	@ValueMapValue 
	String getFirstName();

	@ValueMapValue 
	String getLastName();
	
	@ValueMapValue 
	String getEmail();
		
	@ChildResource 
    List<AdditionalFormItem> getAdditionalFormItems();
	

}
