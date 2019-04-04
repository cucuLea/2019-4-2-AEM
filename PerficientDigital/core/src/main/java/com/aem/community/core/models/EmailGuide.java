package com.aem.community.core.models;

import java.util.Calendar;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.settings.SlingSettingsService;

import com.day.cq.commons.Externalizer;

public class EmailGuide implements Comparable<EmailGuide>{
	private static String ipAndPort="10.2.20.35:4502";
	private String title;
	private String path;
	private Calendar lastModified;
	private String url;
	
	SlingSettingsService slingSettingsService;
	
//	static {
//		try {
//            InetAddress address = InetAddress.getLocalHost();
//       } catch (UnknownHostException e) {
//           e.printStackTrace();
//      } 
//	}
	

	public EmailGuide() {
	}
	
	public EmailGuide(String title, String path, Calendar lastModified,ResourceResolver resolver) {
		Externalizer externalizer = resolver.adaptTo(Externalizer.class);
		this.title = title;
		this.path = path;
		this.lastModified = lastModified;
		//slingSettingsService.RUN_MODES_PROPERTY;
		this.url = externalizer.authorLink(resolver, path) + ".html";
		//this.url=ipAndPort+path+".html";
	}
	
	@Override
	public String toString() {
		return "----- [title=" + title + ", path=" + path + ", lastModified=" + lastModified + ", url="
				+ url + "]-----\n";
	}

	@Override
	public int compareTo(EmailGuide emailGuide) {
		
		return emailGuide.getLastModified().compareTo(this.lastModified);
	}
	
		
	public static String getIpAndPort() {
		return ipAndPort;
	}

	public static void setIpAndPort(String ipAndPort) {
		EmailGuide.ipAndPort = ipAndPort;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Calendar getLastModified() {
		return lastModified;
	}

	public void setLastModified(Calendar lastModified) {
		this.lastModified = lastModified;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
