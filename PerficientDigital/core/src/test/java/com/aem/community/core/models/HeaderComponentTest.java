package com.aem.community.core.models;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.aem.community.core.utils.Validation;

import junitx.util.PrivateAccessor;



public class HeaderComponentTest {
	
	
	@Before
	public void setup() throws Exception{
		HeaderComponent headerComponent=mock(HeaderComponent.class);
		
	}

	@Ignore
	@Test
	public void testInit() throws Exception{
		HeaderComponent headerComponent=new HeaderComponent();
		Validation validaion=mock(Validation.class);
		List<NavigationItem> rightLink=new ArrayList<NavigationItem>();
		rightLink.add(new NavigationItem("a",""));
		rightLink.add(new NavigationItem("b",""));
		PrivateAccessor.setField(headerComponent, "rightLink", rightLink);
		PrivateAccessor.setField(headerComponent, "validaion", validaion);

	}

	@Ignore
	@Test
	public void testGetNavigationItems() {		
//		List<NavigationItem> navigationItems=new ArrayList<NavigationItem>();
//		when(headerComponent.getNavigationItems()).thenReturn(navigationItems);
//		assertNotNull(headerComponent.getNavigationItems());		
	}

	@Ignore
	@Test
	public void testGetLogoLinkUrl() throws Exception{
		HeaderComponent headerComponent=new HeaderComponent();
		Validation validaion=mock(Validation.class);
		when(validaion.validateUrl(eq("/content/PerficientDigital/hello/services"),any(ResourceResolver.class))).thenReturn("/content/PerficientDigital/hello/services.html");
		PrivateAccessor.setField(headerComponent, "validaion", validaion);
		String logoLinkUrl="/content/PerficientDigital/hello/services";
		PrivateAccessor.setField(headerComponent, "logoLinkUrl", logoLinkUrl);
		assertEquals("/content/PerficientDigital/hello/services.html",headerComponent.getLogoLinkUrl());
	
	}

	@Test
	public void testGetLogoAltText(){
		
	
	}


}
