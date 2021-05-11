package org.sid.service;

import org.sid.entities.AppUser;
import org.sid.repo.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CurrentUser {
	
	@Autowired
	private AppUserRepository appUserRepository;
	public AppUser getCurrentUser() {
		
		  Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		  String username;
		  if (principal instanceof UserDetails) {
		     username = ((UserDetails)principal).getUsername();
		     System.out.println("((UserDetails)principal).getUsername()");
		     
		  } else {
			  username = principal.toString();
			  System.out.println("principal.toString();");
		  }
		  
		  return appUserRepository.findByUsername(username);
		  
	}

}
