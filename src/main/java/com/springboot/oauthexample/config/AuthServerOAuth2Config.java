package com.springboot.oauthexample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@EnableAuthorizationServer
@Configuration
public class AuthServerOAuth2Config extends AuthorizationServerConfigurerAdapter {
    
    private final AuthenticationManager authenticationManager;
    private final AppConfig appConfig;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    public AuthServerOAuth2Config(AuthenticationManager authenticationManager, AppConfig appConfig) {
        this.authenticationManager = authenticationManager;
        this.appConfig = appConfig;
    }
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(appConfig.dataSource());
    }
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()");
    }
    
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(appConfig.tokenStore()).userDetailsService(userDetailsService); // Persist the tokens in the database
    }
    
    

}
