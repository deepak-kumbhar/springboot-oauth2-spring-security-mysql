package com.springboot.oauthexample.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.oauthexample.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository accountRepository) {
        this.userRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	Optional<com.springboot.oauthexample.entities.User> user = userRepository.findByUsername(username);
    	String role = user.get().getRoles().iterator().next().getName();
    	System.out.println("Username: "+user.get().getUsername());
    	System.out.println("role: "+role);
        return userRepository
                .findByUsername(username)
                .map(account -> new User(account.getUsername(), account.getPassword(), AuthorityUtils.createAuthorityList(role)))
                .orElseThrow(() -> new UsernameNotFoundException("Could not find " + username));
    }
    
    public void saveUser(com.springboot.oauthexample.entities.User user) {
		userRepository.save(user);
	}
}
