package com.aem.community.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.Servlet;

import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.commerce.common.ValueMapDecorator;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.aem.community.core.service.FooterSocialMediaService;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;


@Component(service = Servlet.class, property = {
		  "sling.servlet.methods=" + HttpConstants.METHOD_GET,
		  "sling.servlet.paths=" + "/bin/perficientDigital/footerSocialMediaServlet"})
public class FooterSocialMediaServlet extends SlingAllMethodsServlet{
	
	private final static Logger log =LoggerFactory.getLogger(FooterSocialMediaServlet.class);  
	
	private String[] socialMediaInfo;
		
	@Reference
	FooterSocialMediaService footersocialMediaService;

	@Override
	protected void doGet(SlingHttpServletRequest request,SlingHttpServletResponse response) throws IOException {
		ResourceResolver resolver = request.getResourceResolver();		
		socialMediaInfo=footersocialMediaService.getSocialMediaInfo();       //拿到config中的数据
		
		List<Resource> mediaResourceList = new ArrayList<Resource>();	
		ValueMap selectItem = null;
		
		for(String info:socialMediaInfo) {		
			if(info.split(";").length<3) {
				continue;                                   //判断格式是否对
			}
			//String value=info;
			String text=info.split(";")[0];
			selectItem=new ValueMapDecorator(new HashMap<String, Object>());
			selectItem.put("value", text);
			selectItem.put("text",text);
			mediaResourceList.add(new ValueMapResource(resolver, new ResourceMetadata(), "nt:unstructured", selectItem));
		}
		
		DataSource ds = new SimpleDataSource(mediaResourceList.iterator());
		request.setAttribute(DataSource.class.getName(), ds);	

	}
}
