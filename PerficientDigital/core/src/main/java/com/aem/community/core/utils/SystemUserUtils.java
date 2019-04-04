package com.aem.community.core.utils;


import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemUserUtils {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private ResourceResolver resolver;
	
	
	public ResourceResolver  getResourceResolver(ResourceResolverFactory resourceResolverFactory) {
		Map<String, Object> param = new HashMap<String, Object>();
        param.put(ResourceResolverFactory.SUBSERVICE, "datapersist");

			try {
				resolver= resourceResolverFactory.getServiceResourceResolver(param);
			} catch (LoginException e) {
				log.error(e.getMessage(), e);
			}
				
		return resolver;
	}
}
