package com.aem.community.core.models;

import static org.junit.Assert.*;

import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.junit.Rule;
import io.wcm.testing.mock.aem.junit.AemContext;


public class AdditionalFormItemTest {
	
	private AdditionalFormItem additionalFormItem;

	private String MOCK_JSON = "/perficientDigital/mockJson/additionalFormItemTest.json";
	 
	private String TEST_CONTENT_ROOT = "/content/PerficientDigital/hello/jcr:content/footerIparsys/contact_us";
	 
	private String ITEM0_RESOURCE = TEST_CONTENT_ROOT + "/additionalFormItems/item0";
	@Rule
	public final AemContext ctx = new AemContext();
	
	@Before
	public void setUp() {
		 ctx.addModelsForClasses(AdditionalFormItem.class);
		 ctx.load().json(MOCK_JSON, TEST_CONTENT_ROOT);

	}

	@Ignore
	@Test
	public void testGetLabel() {
	    String expected = "Phone";
	    ctx.currentResource(ITEM0_RESOURCE);
	    
		additionalFormItem = ctx.request().adaptTo(AdditionalFormItem.class);
		String actual = additionalFormItem.getLabel();
		assertEquals(expected, actual);
	}

	@Ignore
	@Test
	public void testGetIsRequired() {
		fail("Not yet implemented");
	}

}
