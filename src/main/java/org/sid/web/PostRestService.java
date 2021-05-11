package org.sid.web;

import java.util.List;
import java.util.Optional;

import org.sid.entities.AppUser;
import org.sid.entities.Post;
import org.sid.repository.AppUserRepository;
import org.sid.repository.PostRepository;
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

	/*
	 * @RequestMapping(value = "/mmm ", method = RequestMethod.GET) public String
	 * mmmm(){
	 * 
	 * 
	 * 
	 * AppUser user1 = userRepository.findByUsername(f.getUsername1()); AppUser
	 * user2 = userRepository.findByUsername(f.getUsername2()); List<String> list =
	 * user1.getFollowersList(); System.out.println(list); for(int
	 * i=0;i<list.size();i++) {
	 * 
	 * if(list.get(i).equals(user2.get_id())) { System.out.println(list.get(i));
	 * 
	 * System.out.println(user2.get_id()); System.out.println("------------------");
	 * 
	 * list.remove(i); }
	 * 
	 * } user1.setFollowersList(list); return userRepository.save(user1);
	 * 
	 * 
	 * return null; }
	 */
	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	public List<Post> getPosts() {
		return postRepository.findAll();
	}
	
	@RequestMapping(value = "/posts/like", method = RequestMethod.PUT)
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

	@RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
	public Optional<Post> getPost(@PathVariable String id) {
		return postRepository.findById(id);
	}

	@RequestMapping(value = "/posts", method = RequestMethod.POST)
	public Post savePost(@RequestBody Post c) {
		System.out.println(c.getDescripton());
		return postRepository.save(c);
	}

	@RequestMapping(value = "/posts/{id}", method = RequestMethod.DELETE)
	public boolean deletePost(@PathVariable String id) {
		postRepository.deleteById(id);
		return true;
	}

	@RequestMapping(value = "/posts/{id}", method = RequestMethod.PUT)
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
