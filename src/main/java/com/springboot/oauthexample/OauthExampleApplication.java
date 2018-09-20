package com.springboot.oauthexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@SpringBootApplication
@EnableJpaAuditing
public class OauthExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthExampleApplication.class, args);
	}
}
