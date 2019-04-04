package com.aem.community.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.community.core.utils.Validation;

@Model(adaptables=Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeaderComponent {
	//Logger logger =LoggerFactory.getLogger(HeaderComponentModel.class);       
    
    @ChildResource
    private List<NavigationItem> navigationItems;
    
    @ChildResource @Named("links")
    private List<NavigationItem> rightLinks;
	 
    @Inject
    private ResourceResolver resourceResolver;
    
    @Self 
    Resource resource;
    	
	@ValueMapValue 
	private String logoLinkUrl;
	
	@ValueMapValue @Optional
	private String logoImageUrl;
	
	@ValueMapValue @Optional
	private String logoAltText;	
       
	private Validation validaion=new Validation();
	
    @PostConstruct
    protected void init() {   	
    	for(NavigationItem item : navigationItems) {    
    		String linkUrl=item.getLinkUrl();
    		List<NavigationItem> childPages=null; 
    		try{childPages=getChildPages(linkUrl);}catch(Exception e){childPages=null;}   //若是站外网址则没有childPages		
    		item.setChildPages(childPages);    		
    		item.setLinkUrl(validaion.validateUrl(linkUrl,resourceResolver));          
   
    	}		
    	
    	for(NavigationItem item : rightLinks) {
    		String linkUrl=item.getLinkUrl();
    		item.setLinkUrl(validaion.validateUrl(linkUrl,resourceResolver)); 
    	}
    }
    
	//-----------get() start--------------------------------//
    public List<NavigationItem> getNavigationItems() {
    	return navigationItems;    	
    }
    
    public List<NavigationItem> getRightLinks() {
    	return rightLinks;    	
    }
    
    public String getLogoLinkUrl() {
    	return validaion.validateUrl(logoLinkUrl,resourceResolver);
    }
    
    public String getLogoImageUrl() {
    	String exImageStoreUrl="/logo-image-url";
    	//String exImageStoreUrl="/content/PerficientDigital/hello/jcr:content/headerIparsys/header/logo-image-url";
    	logoImageUrl=validaion.validateImageUrl(logoImageUrl,exImageStoreUrl,resource);
    	return logoImageUrl;
    }
    
    public String getLogoAltText() {
    	return logoAltText;
    }
    
    //-----------get() end----------------------------//
    
    private List<NavigationItem> getChildPages(String parentUrl) {   	
    	List<NavigationItem> childPages=new ArrayList<NavigationItem>();
    	Resource itemResource=resourceResolver.getResource(parentUrl);
    	for(Resource item:itemResource.getChildren()) {
    		String linkName=item.getName();
    		if(!"jcr:content".equals(linkName)) {
    			NavigationItem childPage = new NavigationItem(linkName,item.getPath());
    			childPages.add(childPage);
    		}   		
    	}    	
    	return childPages;
    }
      
}
