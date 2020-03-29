package com.example.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dao.UserRepository;
import com.example.entity.User;
 
@Service
public class UserManagementService implements UserDetailsService {
 
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

	@Autowired
    private UserRepository userRepository;
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	User user = userRepository.findByUsername(username);
    	
    	// TODO this must be refactored to a more complex realtion User<>Role
    	// as soon as there are several roles to manage. but it will do fine for now
    	List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
    	if(user.isAdmin()) {
    		GrantedAuthority authority = new SimpleGrantedAuthority(ROLE_ADMIN);
    		grantList.add(authority);
    	}

    	UserDetails userDetails = createSpringUser(user, grantList);

    	return userDetails;
    }

	private org.springframework.security.core.userdetails.User
		createSpringUser(User user, List<GrantedAuthority> grantList) {
			return new org.springframework.security.core.userdetails.User(user.getUsername(),
	                user.getEncrytedPassword(), grantList);
	}
 
}