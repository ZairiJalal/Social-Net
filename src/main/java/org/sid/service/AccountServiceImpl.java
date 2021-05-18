package org.sid.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
import org.sid.entities.Post;
import org.sid.repository.AppRoleRepository;
import org.sid.repository.AppUserRepository;
import org.sid.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	
    @Autowired 
	private AppUserRepository appUserRepository;
    @Autowired
    private AppRoleRepository appRoleRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	 
     @Override
     public AppUser addNewUser(AppUser appUser) {
			
			
			  String pw=appUser.getPassword(); 
			  appUser.setPassword(passwordEncoder.encode(pw));
				/*
				 * try { appUser.setPictureData(appUser.getPicture().getBytes()); } catch
				 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
				 */
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
	@Override
	public List<AppUser> followersListUsers(String id) {
		AppUser appUser = appUserRepository.findBy_id(id);
		List<String> list = appUser.getFollowersList();
		List<AppUser> listAppUser = new ArrayList<AppUser>();
		for(int i=0;i<list.size();i++) {
			listAppUser.add(appUserRepository.findBy_id(list.get(i)));
		}
		return listAppUser;
	}
	@Override
	public List<AppUser> followingsListUsers(String id) {
		AppUser appUser = appUserRepository.findBy_id(id);
		List<String> list = appUser.getFollowingsList();
		List<AppUser> listAppUser = new ArrayList<AppUser>();
		for(int i=0;i<list.size();i++) {
			listAppUser.add(appUserRepository.findBy_id(list.get(i)));
		}
		return listAppUser;
	}
	@Override
	public List<Post> followersListPost(String id) {
		AppUser appUser = appUserRepository.findBy_id(id);
		List<String> list = appUser.getFollowersList();
		List<Post> listPost = new ArrayList<Post>();
		for(int i=0;i<list.size();i++) {
			listPost.addAll(postRepository.findByUserId(list.get(i)));
		}
		return listPost;
	}
	@Override
	public List<Post> followingsListPost(String id) {
		AppUser appUser = appUserRepository.findBy_id(id);
		List<String> list = appUser.getFollowingsList();
		List<Post> listPost = new ArrayList<Post>();
		for(int i=0;i<list.size();i++) {
			listPost.addAll(postRepository.findByUserId(list.get(i)));
		}
		return listPost;
	}
}


