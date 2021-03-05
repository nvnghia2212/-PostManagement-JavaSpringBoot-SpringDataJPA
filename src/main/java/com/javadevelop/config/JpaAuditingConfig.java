package com.javadevelop.config;

import java.util.Optional;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration //Chỉ định class java bình thường là java config
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {
	
	@Bean
	public AuditorAware<String> auditorProvider(){
		return new AuditorAwareImpl();
	}
	
	public static class AuditorAwareImpl implements AuditorAware<String> {

		@Override
		public String getCurrentAuditor() {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(authentication == null || !authentication.isAuthenticated()) {
				return null;
			}
			return authentication.getName();
		}
		
	}
	
}
