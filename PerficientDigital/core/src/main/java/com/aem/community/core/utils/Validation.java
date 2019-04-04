package com.aem.community.core.utils;



import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

public class Validation {
	
	 public String validateUrl(String linkUrl,ResourceResolver resourceResolver) {    //if it's external or internal websites
	    	
	    	if(linkUrl==null) {return null;}	    	
	    	if(resourceResolver.getResource(linkUrl)!=null) {
	    		linkUrl=linkUrl+".html";
	    	}   	
	    	return linkUrl; 
	    }    
	 
	 public String validateImageUrl(String ImageUrl,String exImageStoreUrl,Resource resource) {   //if it's external or internal image source
		 	String parentUrl=resource.getPath();
		 	String externalUrl=parentUrl+exImageStoreUrl;
		 	String newImageUrl=null;
		 	if(ImageUrl==null) {	 		
		 		newImageUrl=externalUrl;
		 	}
		 	else {
		 		newImageUrl=ImageUrl;
		 	}	 
	    	return newImageUrl;
	    } 
}
