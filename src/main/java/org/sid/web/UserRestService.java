package org.sid.web;
 
import java.util.ArrayList;
import java.util.Collection;
import java.util.List; import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
import org.sid.repository.AppUserRepository;
import org.sid.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
  
  @RestController
  
  @CrossOrigin(origins = "*", allowedHeaders = "*")
  
  public class UserRestService {
  
	  @Autowired private AppUserRepository userRepository;
	  @Autowired private AccountService accountService;
  
  
  @RequestMapping(value = "/Users/register", method = RequestMethod.POST) 
  public AppUser saveUser(@RequestBody AppUser c){   	  
		  accountService.addNewUser(c); 
		  return c;
  }
  
  
  @RequestMapping(value = "/Users", method = RequestMethod.GET) 
  public List<AppUser> getUsers(){ 
	  return userRepository.findAll(); 
  }
  @RequestMapping(value = "/Users/{id}", method = RequestMethod.GET) 
  public AppUser getUser(@PathVariable String id){
	  return userRepository.findBy_id(id);
  }
  
  @RequestMapping(value = "/Users/followers/{id}", method = RequestMethod.GET) 
  public List<AppUser> followersUsers(@PathVariable String id){ 
	  return accountService.followersListUsers(id); 
  }
  @RequestMapping(value = "/Users/followings/{id}", method = RequestMethod.GET) 
  public List<AppUser> followingsUsers(@PathVariable String id){ 
	  return accountService.followingsListUsers(id); 
  }
  
  @RequestMapping(value = "/Users/{id}", method = RequestMethod.DELETE) 
  public boolean deleteUser(@PathVariable String id){ 
	  userRepository.deleteById(id);
      return true; 
  }
  
  @RequestMapping(value = "/Users/{id}", method = RequestMethod.PUT) 
  public AppUser editeUser(@PathVariable String id , @RequestBody AppUser c){ 
	  AppUser u = userRepository.findBy_id(id);
	  u.set_id(id); 
	  return  userRepository.save(c); 
  } 
  @RequestMapping(value = "/Users/follow", method = RequestMethod.PUT) 
  public AppUser followUser(@RequestBody Follow f){ 	  

		System.out.println(f.getIduser1());
		System.out.println(f.getIduser2());
		
	  AppUser user1 = userRepository.findBy_id(f.getIduser1());
	  AppUser user2 = userRepository.findBy_id(f.getIduser2());
	  
	  List<String> list = user1.getFollowersList(); 
	  for(int i=0;i<list.size();i++) {		  		  
		  if(list.get(i).equals(user2.get_id()))
			  return null;
	  }
			  
		 
	  list.add(user2.get_id()); 
	  user1.setFollowersList(list); 
	  
	  List<String> list2 = user2.getFollowingsList(); 
	  list2.add(user1.get_id());
	  user2.setFollowingsList(list2); 
	  userRepository.save(user2); 
	  return userRepository.save(user1);
  } 
 
 }
  @Data
  class Follow {
	  private String iduser1;
	  private String iduser2;
	  
  }
 