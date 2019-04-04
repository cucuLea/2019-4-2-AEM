package com.aem.community.core.schedulers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.acs.commons.email.EmailService;
import com.aem.community.core.models.EmailGuide;
import com.day.cq.wcm.api.Page;

@Component(
		immediate = true, 
		configurationPid = "com.aem.community.core.schedulers.EmailScheduledTask",
		service = Runnable.class
		)
@Designate(ocd = EmailScheduledTask.Configuration.class)

public class EmailScheduledTask implements Runnable{

    public static final String CONFIG_PID = "com.aem.community.core.schedulers.EmailScheduledTask";  
    public static final String NAME = "Email Scheduler";  
    public static final String TEMPLATE_PATH="/etc/notification/email/acs-commons/perficient-digital-email-guides.txt"; 
	
    private final Logger logger = LoggerFactory.getLogger(getClass());
	private String parentPageUrl;
	private int number;
    
	@Reference
	private ResourceResolverFactory resourceResolverFactory;
	
	private ResourceResolver resourceResolver;
	private Resource parentResource;
	private Resource contactUsersResource;
	
	@Reference
	EmailService emailService;
	
	
    @ObjectClassDefinition(name = "PerficientDigitalEmailScheduled - OSGi")
	public @interface Configuration {

		@AttributeDefinition(name = "Parent page", description = "Parent page of guide pages", type = AttributeType.STRING)
		String parentPage() default "/content/PerficientDigital/hello/insights/";

		@AttributeDefinition(name = "Number", description = "Number of guides to send", type = AttributeType.INTEGER)
		int number() default 1;

		@AttributeDefinition(name = "Concurrent", description = "Schedule task concurrently", type = AttributeType.BOOLEAN)
		boolean scheduler_concurrent() default false;

		@AttributeDefinition(name = "Expression", description = "Cron-job expression. Default: run every minute.", type = AttributeType.STRING)
		String scheduler_expression() default "0 0/1 * 1/1 * ? *";
	}

	@Activate
	protected void activate(Configuration config) {
		parentPageUrl = config.parentPage();
		number = config.number();
	}
	@Override  
    public void run() {  
        //logger.info("-----------> Scheduler Running: {}", parentPageUrl);         
        sendGuides();
    } 
	
	private void sendGuides() {
		Map<String, Object> param = new HashMap<String, Object>();
        param.put(ResourceResolverFactory.SUBSERVICE, "datapersist");

			try {
				resourceResolver = resourceResolverFactory.getServiceResourceResolver(param);
			} catch (LoginException e) {
				logger.error(e.getMessage(), e);
			}
		
		String[] contactUserList = getRecipients();
		String guides =getGuidesInfo();
		
		//String templatePath="/etc/notification/email/acs-commons/perficient-digital-email-guides.txt";
		
		Map<String, String> emailParams = new HashMap<String,String>();
		emailParams.put("emailGuidesInfo",guides);
		
		List<String> failureList = emailService.sendEmail(TEMPLATE_PATH, emailParams, contactUserList);
		
		if (failureList.isEmpty()) {
			logger.info("Email sent successfully to the recipients");
		} else {
			logger.info("Email sent failed");
		}
		
	}

	private String[] getRecipients()  {
		List<String> recipients=new ArrayList<String>();
		contactUsersResource = resourceResolver.getResource("/content/PerficientDigital/customerInfo");
		Iterator<Resource> contactUsers=contactUsersResource.getChildren().iterator();
		while (contactUsers.hasNext()) {
			Resource contactUser = contactUsers.next();
			Node contactUserNode = contactUser.adaptTo(Node.class);
			try {
				String email=contactUserNode.getProperty("workEmail").getValue().getString();
				recipients.add(email);
			} catch (PathNotFoundException e) {
				logger.error(e.getMessage(), e);
			} catch (RepositoryException e) {
				logger.error(e.getMessage(), e);
			}			
		}
		return recipients.toArray(new String[recipients.size()]);
	}
	
	private String getGuidesInfo() {
		List<EmailGuide> emailGuideList=getGuides();
		String guidesInfo="";
		for(EmailGuide item:emailGuideList) {
			guidesInfo+=item.getTitle()+":"+item.getUrl()+"\n";
		}
		return guidesInfo;
	}
		
	private  List<EmailGuide> getGuides() {
		parentResource=resourceResolver.getResource(parentPageUrl);
		Page parentPage = parentResource.adaptTo(Page.class);
		Iterator<Page> childPages = parentPage.listChildren();

		
		List<EmailGuide> emailGuideList =new ArrayList<EmailGuide>();
		while (childPages.hasNext()) {
			Page childPage = childPages.next();
			String title = childPage.getTitle();
			String path = childPage.getPath();
			Calendar lastModified = childPage.getLastModified();
			EmailGuide emailGuide = new EmailGuide(title, path, lastModified,resourceResolver);
			emailGuideList.add(emailGuide);
		}
		Collections.sort(emailGuideList);
		
		if(emailGuideList.size()>number) {
			return emailGuideList.subList(0, number);
		}
		else {
			return emailGuideList;
		}
		
	}
}
