package org.sid.web;

import java.util.List;
import java.util.Optional;

import org.sid.entities.AppUser;
import org.sid.entities.Post;
import org.sid.repository.AppUserRepository;
import org.sid.repository.PostRepository;
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
	@RequestMapping(value = "/Posts/followers/{id}", method = RequestMethod.GET)
	public List<Post> getfollowersPosts(@PathVariable String id) {
		return accountService.followersListPost(id);
	}
	@RequestMapping(value = "/Posts/followings/{id}", method = RequestMethod.GET)
	public List<Post> getfollowingsPosts(@PathVariable String id) {
		return accountService.followingsListPost(id);
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
	public Post savePost(@RequestBody Post c) {
		System.out.println(c.getDescripton());
		return postRepository.save(c);
	}

	@RequestMapping(value = "/Posts/{id}", method = RequestMethod.DELETE)
	public boolean deletePost(@PathVariable String id) {
		postRepository.deleteById(id);
		return true;
	}

	@RequestMapping(value = "/Posts/{id}", method = RequestMethod.PUT)
	public Post editePost(@PathVariable String id, @RequestBody Post c) {
		c.set_id(id);
		return postRepository.save(c);
	}
}
@Data
class Like {
	private String username;
	private String idPost;
}
