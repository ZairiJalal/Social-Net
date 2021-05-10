package org.sid.web;
 
import java.util.ArrayList;
import java.util.List; import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.sid.entities.AppUser;
import org.sid.repository.AppUserRepository;
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
  
  @RequestMapping(value = "/users", method = RequestMethod.GET) 
  public List<AppUser> getUsers(){ 
	  return userRepository.findAll(); 
  }
  
  
  @RequestMapping(value = "/users/{id}", method = RequestMethod.GET) 
  public Optional<AppUser> getUser(@PathVariable String id){
	  return userRepository.findBy_id(id);
  }
  
  @RequestMapping(value = "/users", method = RequestMethod.POST) 
  public AppUser saveUser(@RequestBody AppUser c){ 
	   AppUser u = userRepository.save(c); 
	   u.setIdUser(u.get_id());	   
	   return u;
  }
  
  @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE) 
  public boolean deleteUser(@PathVariable String id){ 
	  userRepository.deleteById(id);
      return true; 
  }
  
  @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT) 
  public AppUser editeUser(@PathVariable String id , @RequestBody AppUser c){ 
	  AppUser u = userRepository.findByIdUser(id);
	  u.set_id(id); 
	  return  userRepository.save(c); 
  } 
  @RequestMapping(value = "/users/follow", method = RequestMethod.PUT) 
  public AppUser followUser(@RequestBody Follow f){ 	  

		/*
		 * AppUser user1 = userRepository.findByUsername(f.getUsername1()); AppUser
		 * user2 = userRepository.findByUsername(f.getUsername2());
		 * 
		 * List<String> list = user1.getFriendsList(); for(int i=0;i<list.size();i++) {
		 * if(list.get(i).equals(user2.get_id())) {
		 * 
		 * return user1; }
		 * 
		 * } list.add(user2.get_id()); user1.setFriendsList(list); return
		 * userRepository.save(user1);
		 */
	  return null;
  } 
  @RequestMapping(value = "/users/unfollow ", method = RequestMethod.PUT) 
  public AppUser unfollowUser(@RequestBody Follow f){ 
		/*
		 * System.out.println(f.getUsername1()); System.out.println(f.getUsername2());
		 * AppUser user1 = userRepository.findByUsername(f.getUsername1()); AppUser
		 * user2 = userRepository.findByUsername(f.getUsername2()); List<String> list =
		 * user1.getFriendsList(); for(int i=0;i<list.size();i++) {
		 * if(list.get(i).equals(user2.get_id())) { System.out.println(list.get(i));
		 * System.out.println(user2.get_id()); System.out.println("------------------");
		 * list.remove(i); }
		 * 
		 * } user1.setFriendsList(list); return userRepository.save(user1);
		 */ return null;
  } 
 }
  @Data
  class Follow {
	  private String username1;
	  private String username2;
	  
  }
 