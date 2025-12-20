package com.trade.sevice.impl;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



	@Service
	public class UserDetailsServiceImpl implements UserDetailsService {

	    @Override
	    public UserDetails loadUserByUsername(String username)
	            throws UsernameNotFoundException {

	        // Example in-memory user
	        return User.builder()
	                .username("admin")
	                .password(new BCryptPasswordEncoder().encode("password"))
	                .roles("USER")
	                .build();
	    }
}
