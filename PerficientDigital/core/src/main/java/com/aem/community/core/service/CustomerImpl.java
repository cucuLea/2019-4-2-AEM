package com.aem.community.core.service;

//DS Annotations
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.LoginException;
import javax.jcr.Node;
import java.util.HashMap;
import java.util.Map;

//Sling Imports
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ResourceResolver;


@Component(
		service=ICustomerInter.class
		)
public class CustomerImpl implements ICustomerInter {
	@Reference
	private Repository repository;
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Session session;

	// Inject a Sling ResourceResolverFactory
	@Reference
	private ResourceResolverFactory resolverFactory;

	@Override
	public void addCustomerData(String firstName, String lastName, String workEmail) {
		 Map<String, Object> param = new HashMap<String, Object>();
         param.put(ResourceResolverFactory.SUBSERVICE, "datapersist");
         ResourceResolver resolver = null;
         
//       final Credentials creds = new SimpleCredentials("admin", "admin".toCharArray());
         try {   
             //Invoke the adaptTo method to create a Session used to create a QueryManager       	 
             resolver = resolverFactory.getServiceResourceResolver(param);
             session = resolver.adaptTo(Session.class);
//           session = repository.login(creds);
             //Create a node that represents the root node
             Node root = session.getRootNode(); 
           //Get the content node in the JCR
             Node content = root.getNode("content/PerficientDigital/customerInfo");
                        
             String numberValue = "customer"+System.currentTimeMillis();
             
             Node customerRoot = content.addNode(numberValue,"nt:unstructured");             
             customerRoot.setProperty("firstName", firstName); 
             customerRoot.setProperty("lastName", lastName); 
             customerRoot.setProperty("workEmail", workEmail); 
              
             session.save(); 
             session.logout();
         }
         catch(LoginException e) {
        	 logger.error("login"+e.getMessage());
         }
         catch(Exception e)
         {
             logger.error("hhhhh-----"+e.getMessage());
         }
                           

	}

}
