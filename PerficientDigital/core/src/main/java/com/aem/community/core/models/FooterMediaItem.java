package com.aem.community.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.aem.community.core.service.FooterSocialMediaService;


@Model(adaptables=Resource.class)
public class FooterMediaItem {
	
	@ValueMapValue @Named("Icons") @Optional
	private String mediaInfo;
	
	@Inject
	FooterSocialMediaService footersocialMediaService;
	
	private String css;
	private String link;
	
	@PostConstruct
	public void init() {
		String[] allInfo=footersocialMediaService.getSocialMediaInfo();
		if(allInfo!=null) {
			for(String item:allInfo) {
				if(item.split(";").length<3) {
					continue;
				}
				if(item.split(";")[0].equals(mediaInfo)) {
					css=item.split(";")[1];
					link=item.split(";")[2];
				}
			}
		}
	}
	
	
	public String getCss() {
		return css;

	}
	
	public String getLink() {
		return link;
	}

}
