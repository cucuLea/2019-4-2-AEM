package com.aem.community.core.workflow;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.acs.commons.email.EmailService;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.framework.Constants;


@Component(service = WorkflowProcess.class,
		property= {
				Constants.SERVICE_DESCRIPTION + "=Test Email workflow process implementation.",
				Constants.SERVICE_VENDOR+"=Adobe",
				"process.label"+"=Test Email Workflow Process"
})
public class SendEmail  implements WorkflowProcess{
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Reference
	EmailService emailService;
	
	@Override
	public void execute(WorkItem item, WorkflowSession wfsession, MetaDataMap arg2) throws WorkflowException {
		ResourceResolver resolver = wfsession.adaptTo(ResourceResolver.class); 
		UserManager userManager = resolver.adaptTo(UserManager.class);
		
		log.info("-----Here in execute method");
		final String initiator = item.getWorkflow().getInitiator();
		try {
			Authorizable userAuthorizable = userManager.getAuthorizable(initiator);
			String userEmail = PropertiesUtil.toString(userAuthorizable.getProperty("profile/email"),""); 
			String contentPath=item.getContentPath();
			
			String contentUrl=getContentUrl(contentPath,resolver);
			String title=getTitle(contentPath,resolver);
			
			sendEmail(userEmail,contentUrl,title);
			
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
		}

	}
	
	private String getContentUrl(String contentPath,ResourceResolver resolver) {
		Externalizer externalizer = resolver.adaptTo(Externalizer.class);
		String contenUrl=externalizer.authorLink(resolver, contentPath) + ".html";
		return contenUrl;
	}
	
	private String getTitle(String contentPath,ResourceResolver resolver) {
		Resource contentResource =resolver.getResource(contentPath);
		Page resourcePage = contentResource.adaptTo(Page.class);
		String title=resourcePage.getTitle();	
		return title;
	}
	
	private void sendEmail(String userEmail,String contentUrl,String title) {
		if(userEmail==null) {
			log.info("no email address!");
			return;
		}
		
		String templatePath="/etc/notification/email/acs-commons/perficient-digital-workflow-email.txt";
		
		String publishInfo=title+": "+contentUrl;
		
		Map<String, String> emailParams = new HashMap<String,String>();
		emailParams.put("publishInfo",publishInfo);
		
		List<String> failureList = emailService.sendEmail(templatePath, emailParams, userEmail);
		
		if (failureList.isEmpty()) {
			log.info("Email sent successfully to the recipients");
		} else {
			log.info("Email sent failed");
		}
		
	}

}
