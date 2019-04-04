package com.aem.community.core.models;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ResourcePath;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.aem.community.core.utils.Validation;

@Model(adaptables=Resource.class)
public class NavigationItem {
	
	@ValueMapValue
	private String linkName;
	
	@ValueMapValue
	private String linkUrl;
	
	@Optional 
	private List<NavigationItem> childPages;
	
	private Validation validaion=new Validation();
		
	public NavigationItem() {}
 
	public NavigationItem( String linkName,String linkUrl) {
		this.linkName = linkName;
		this.linkUrl = linkUrl;
		this.childPages = null;
	}
		
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getLinkUrl() {
		return linkUrl;     //校验一下是否需要加后缀html
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
	public List<NavigationItem> getChildPages() {
		return childPages;
	}

	public void setChildPages(List<NavigationItem> childPages) {
		this.childPages = childPages;
	}
	
	
}
