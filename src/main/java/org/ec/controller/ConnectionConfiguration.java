package org.ec.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = { "org.ec.controller.*" })
@PropertySource("classpath:application.properties")
public class ConnectionConfiguration {

	@Value("${rest.url}")
	private String url;
	
	public String getUrl() {
		return url;
	}
	
}
