package org.sid.web;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.sid.entities.AppUser;
import org.sid.entities.Post;
import org.sid.repository.AppUserRepository;
import org.sid.repository.PostRepository;
import org.sid.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

public class PostRestService {

	@Autowired
	private PostRepository postRepository;
	@Autowired private AppUserRepository userRepository;
	@Autowired private AccountService accountService;

	
	@RequestMapping(value = "/Posts", method = RequestMethod.GET)
	public List<Post> getPosts() {
		return postRepository.findAll();
	}
	
	@RequestMapping(value = "/Posts/userCurrent/{id}", method = RequestMethod.GET)
	public List<Post> getuserCurrentPosts(@PathVariable String id) {
		return postRepository.findByUserId(id);
	}
	
	
	@RequestMapping(value = "/Posts/like", method = RequestMethod.PUT)
	public Post likePost(@RequestBody Like l) {
		System.out.println(l.getIdPost());
		System.out.println(l.getUsername());
		 AppUser user1 = userRepository.findByUsername(l.getUsername()); 
		 Post post = postRepository.findBy_id(l.getIdPost());
		  
		  List<String> list = post.getLikes(); 
		  for(int i=0;i<list.size();i++) {		  		  
			  if(list.get(i).equals(user1.get_id()))
				  return null;
		  }				  
			 
		  list.add(user1.get_id()); 
		  post.setLikes(list); 
		  		   
		  return postRepository.save(post);
	}

	@RequestMapping(value = "/Posts/{id}", method = RequestMethod.GET)
	public Optional<Post> getPost(@PathVariable String id) {
		return postRepository.findById(id);
	}

	@RequestMapping(value = "/Posts", method = RequestMethod.POST)
	public Post savePost(
			@RequestParam("userId") String userId,
			@RequestParam(value = "description",required = false) String description,
			@RequestParam(value = "picture",required = false) MultipartFile picture) throws IOException {
		Post p = new Post();
		if(description!=null)
		p.setDescription(description);
		p.setUserId(userId);
		if(picture!=null)
		p.setPicture(picture.getBytes());
		postRepository.save(p);
		if(picture!=null) {
		
			String fileDownloadUri = ServletUriComponentsBuilder
	            .fromCurrentContextPath()
	            .path("/Posts/picture/")
	            .path(p.get_id())
	            .toUriString();
		
			p.setPictureUrl(fileDownloadUri);
			}
		return postRepository.save(p);
	}

	@RequestMapping(value = "/Posts/{id}", method = RequestMethod.DELETE)
	public boolean deletePost(@PathVariable String id) {
		postRepository.deleteById(id);
		return true;
	}

	@RequestMapping(value = "/Posts/{id}", method = RequestMethod.PUT)
	public Post editePost(
			@PathVariable String id,
			@RequestParam(value = "description",required = false) String description,
			@RequestParam(value = "picture",required = false) MultipartFile picture) throws IOException {
		Post p = postRepository.findBy_id(id);
		if(description!=null)
			p.setDescription(description);
		if(picture!=null){
			p.setPicture(picture.getBytes());
        	String fileDownloadUri = ServletUriComponentsBuilder
		            .fromCurrentContextPath()
		            .path("/Posts/picture/")
		            .path(p.get_id())
		            .toUriString();
			p.setPictureUrl(fileDownloadUri);				
		}
			return postRepository.save(p);
			
	}
	 @GetMapping("/Posts/picture/{id}")
	  public ResponseEntity<byte[]> getPictrure(@PathVariable String id) throws IOException{
		  Post p = postRepository.findById(id).get();

	    return ResponseEntity.ok()
	    		.contentType(MediaType.IMAGE_PNG)
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "post" + "\"")
	        .body(p.getPicture());
	  }
}
@Data
class Like {
	private String username;
	private String idPost;
}
