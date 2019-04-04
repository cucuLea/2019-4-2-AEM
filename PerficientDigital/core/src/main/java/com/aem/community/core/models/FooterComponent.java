package com.aem.community.core.models;

import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

import com.aem.community.core.utils.Validation;

@Model(adaptables=Resource.class , defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FooterComponent {
	
	@ValueMapValue 
	private String companyLogo;

	@ValueMapValue
	private String logoLink;
	
	@ValueMapValue 
	private String privacyPolicyText;
	
	@ValueMapValue 
	private String sourceLink;
	
	@ValueMapValue 
	private String copyright;
	
	@ValueMapValue 
	private String slogan;    
	
	@Self 
    Resource resource;
	
	@Inject 
	ResourceResolver resourceResolver;
	
	@ChildResource 
	private List<FooterMediaItem> socialMediasItems;
	
	private Validation validaion=new Validation();

	public String getCompanyLogo() {
		String exCompanyLogoUrl="/company-logo";
		//String exCompanyLogoUrl="/content/PerficientDigital/hello/jcr:content/footerIparsys/footer/company-logo";
		companyLogo=validaion.validateImageUrl(companyLogo,exCompanyLogoUrl,resource);
		return companyLogo;
	}

	public String getLogoLink() {
		return validaion.validateUrl(logoLink,resourceResolver);
	}


	public String getPrivacyPolicyText() {
		return privacyPolicyText;
	}


	public String getSourceLink() {
		return sourceLink;
	}

	public String getCopyright() {
		return copyright;
	}

	public String getSlogan() {
		return slogan;
	}

	public List<FooterMediaItem> getSocialMediasItems() {
		return socialMediasItems;
	}

	
}
