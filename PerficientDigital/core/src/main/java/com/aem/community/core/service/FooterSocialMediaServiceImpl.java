package com.aem.community.core.service;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import com.aem.community.core.FooterSocialMediaConfig;



@Component(service = FooterSocialMediaService.class, configurationPolicy = ConfigurationPolicy.REQUIRE,immediate=true)
@Designate(ocd = FooterSocialMediaConfig.class)
public class FooterSocialMediaServiceImpl implements FooterSocialMediaService{

	private FooterSocialMediaConfig config;
		
	@Override
	public String[] getSocialMediaInfo() {
		// TODO Auto-generated method stub
		return config.getSocialMediaInfoList();
	}

	
	@Activate
	protected void Activate(FooterSocialMediaConfig config) {
		this.config=config;
	}
}