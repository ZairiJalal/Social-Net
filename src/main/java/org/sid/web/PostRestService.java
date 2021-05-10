package org.sid.web;
import java.util.List; import java.util.Optional;

import org.sid.entities.Post;
import org.sid.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  
  @CrossOrigin(origins = "*", allowedHeaders = "*")
  
  public class PostRestService {
  
  @Autowired private PostRepository postRepository;
  
  @RequestMapping(value = "/posts", method = RequestMethod.GET) 
  public List<Post> getPosts(){ return postRepository.findAll(); }
  
  
  @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET) 
  public Optional<Post> getPost(@PathVariable String id){ 
	  return postRepository.findById(id);
  }
  
  @RequestMapping(value = "/posts", method = RequestMethod.POST) 
  public Post savePost(@RequestBody Post c){ 
	  System.out.println(c.getDescripton());
	  return postRepository.save(c); 
  }
  
  @RequestMapping(value = "/posts/{id}", method = RequestMethod.DELETE) 
  public boolean deletePost(@PathVariable String id){ 
	  postRepository.deleteById(id);
      return true; 
  }
  
  @RequestMapping(value = "/posts/{id}", method = RequestMethod.PUT) 
  public Post editePost(@PathVariable String id , @RequestBody Post c){ 
	  c.set_id(id); 
	  return  postRepository.save(c); 
  } 
 }
 
