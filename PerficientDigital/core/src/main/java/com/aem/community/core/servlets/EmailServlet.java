package com.aem.community.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.adobe.acs.commons.email.EmailService;
import com.aem.community.core.service.CustomerImpl;
import com.aem.community.core.service.EmailReceiveMemberService;
import com.aem.community.core.service.ICustomerInter;


@Component(service = Servlet.class, property = {
		  "sling.servlet.methods=" + HttpConstants.METHOD_POST,
		  "sling.servlet.paths=" + "/bin/perficientDigital/emailServlet"})
public class EmailServlet extends SlingAllMethodsServlet{

	@Reference
	EmailService emailService;
	
	@Reference 
	EmailReceiveMemberService emailReceiveMemberService;
	
	@Reference
	ICustomerInter iCustomerInter;
	
	@Override
	protected void doGet(SlingHttpServletRequest request,SlingHttpServletResponse response) throws IOException {
		doPost(request, response);
		
	}
	
	@Override
	protected void doPost(SlingHttpServletRequest request,SlingHttpServletResponse response) throws IOException {
		
		String firstName=request.getParameter("firstName");
		String lastName=request.getParameter("lastName");
		String workEmail=request.getParameter("workEmail");
		String[] additionalFieldValue=request.getParameterValues("additionalField-value");
		String[] additionalFieldLabel=request.getParameterValues("additionalField-label");
		
		PrintWriter out=response.getWriter();
				
		String templatePath="/etc/notification/email/acs-commons/perficient-digital-contact-us-email.txt";
		
		Map<String, String> emailParams = new HashMap<String,String>();
		emailParams.put("firstName",firstName);
		emailParams.put("lastName",lastName); 
		emailParams.put("workEmail",workEmail);
		String additionalInfo="";
		if(additionalFieldValue!=null) {                                   //value的值默认为"",
			for(int i=0;i<additionalFieldValue.length;i++) {
			additionalInfo=additionalInfo+additionalFieldLabel[i]+":"+additionalFieldValue[i]+"\n";	
			}
			emailParams.put("additionalInfo",additionalInfo);
		}
				
		String[] recipients = emailReceiveMemberService.getEmailAddress();
				
		List<String> failureList = emailService.sendEmail(templatePath, emailParams, recipients);
		
		if (failureList.isEmpty()) {
			storeData(firstName, lastName, workEmail);
			out.println("Email sent successfully to the recipients");
		} else {
			out.println("Email sent failed");
		}
	}
	
	public void storeData(String firstName, String lastName, String workEmail){
		
		iCustomerInter.addCustomerData(firstName, lastName, workEmail);
	}
	
	
}
