package org.sid.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
import org.sid.repository.AppRoleRepository;
import org.sid.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	
    @Autowired 
	private AppUserRepository appUserRepository;
    @Autowired
     private AppRoleRepository appRoleRepository;
    @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	 
     @Override
     public AppUser addNewUser(AppUser appUser) {
			
			
			  String pw=appUser.getPassword(); 
			  appUser.setPassword(passwordEncoder.encode(pw));
			  return appUserRepository.save(appUser);
			  
     }
     @Override
     public AppRole addNewRole(AppRole appRole) {
           return appRoleRepository.save(appRole);
     }
     @Override
     public void addRoleToUser(String username, String roleName) {
    	 AppUser appUser=appUserRepository.findByUsername(username);
         AppRole appRole=appRoleRepository.findByRoleName(roleName);
         appUser.getAppRoles().add(appRole);
     }
     @Override
     public AppUser loadUserByUsername(String username) { 
    	   return appUserRepository.findByUsername(username);
     }
     @Override
     public List<AppUser> listUsers() {
           return appUserRepository.findAll();
     }
}


