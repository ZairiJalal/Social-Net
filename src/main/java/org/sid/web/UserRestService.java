package org.sid.web;
 

import java.awt.Image;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.nio.file.Path;
import org.springframework.core.io.UrlResource;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.sid.entities.AppUser;
import org.sid.repository.AppUserRepository;
import org.sid.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


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
  @RequestMapping(value = "/Users/picture/{id}", method = RequestMethod.PUT) 
  public AppUser pictureUser(@RequestParam("file") MultipartFile file,@PathVariable String id){  

	  AppUser appUser = userRepository.findBy_id(id);
		  try {
			appUser.setPicture(file.getBytes());
			String fileDownloadUri = ServletUriComponentsBuilder
		            .fromCurrentContextPath()
		            .path("/Users/picture/")
		            .path(id)
		            .toUriString();
			appUser.setPictureUrl(fileDownloadUri);
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return userRepository.save(appUser);
  }
  @GetMapping("/Users/picture/{id}")
  public ResponseEntity<byte[]> getPictrure(@PathVariable String id,HttpServletRequest request) throws IOException{
	  AppUser appUser = userRepository.findById(id).get();

    return ResponseEntity.ok()
    		.contentType(MediaType.IMAGE_PNG)
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + appUser.getUsername() + "\"")
        .body(appUser.getPicture());
  }
  
	/*
	 * @PostMapping("/Users/registerr") public ResponseEntity<AppUser>
	 * addActualite(@RequestBody MultipartFile multipart) throws IOException{
	 * AppUser c = new AppUser(); c.setPassword("2345"); HttpHeaders header =new
	 * HttpHeaders(); c.setPicture(new Binary(BsonBinarySubType.BINARY,
	 * multipart.getBytes())); return new
	 * ResponseEntity<>(accountService.addNewUser(c),header,HttpStatus.CREATED); }
	 */

  
  
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
	  c.set_id(id); 
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
 