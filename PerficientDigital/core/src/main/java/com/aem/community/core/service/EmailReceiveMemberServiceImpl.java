package com.aem.community.core.service;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.Designate;

import com.aem.community.core.EmailReceiveMemberConfig;

@Component(service = EmailReceiveMemberService.class, configurationPolicy = ConfigurationPolicy.REQUIRE,immediate=true)
@Designate(ocd = EmailReceiveMemberConfig.class)
public class EmailReceiveMemberServiceImpl implements EmailReceiveMemberService{
	
		private EmailReceiveMemberConfig config;
			
		@Override
		public String[] getEmailAddress() {
			// TODO Auto-generated method stub
			return config.getEmailAddress();
		}

		
		@Activate
		protected void Activate(EmailReceiveMemberConfig config) {
			this.config=config;
		}
	
}
