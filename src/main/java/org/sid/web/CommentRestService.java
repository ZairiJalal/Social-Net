package org.sid.web;
import java.util.List; import java.util.Optional;

import org.sid.entities.Comment;
import org.sid.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  
  @CrossOrigin(origins = "*", allowedHeaders = "*")
  
  public class CommentRestService {
  
  @Autowired private CommentRepository commentRepository; 
  
  @RequestMapping(value = "/Comments", method = RequestMethod.GET) 
  public List<Comment> getComments(){ return commentRepository.findAll(); }
  
  
  @RequestMapping(value = "/Comments/{id}", method = RequestMethod.GET) 
  public Optional<Comment> getMComment(@PathVariable String id){ 
	  return commentRepository.findById(id);
  }
  @RequestMapping(value = "/Comments/postCurrent/{id}", method = RequestMethod.GET) 
  public List<Comment> getMMComment(@PathVariable String id){ 
	  return commentRepository.findByUserId(id);
  }
  
  @RequestMapping(value = "/Comments", method = RequestMethod.POST) 
  public Comment saveComment(@RequestBody Comment c){ 
	  return commentRepository.save(c); 
  }
  
  @RequestMapping(value = "/Comments/{id}", method = RequestMethod.DELETE) 
  public boolean deleteComment(@PathVariable String id){ 
	  commentRepository.deleteById(id);
      return true; 
  }
  
  @RequestMapping(value = "/Comments/{id}", method = RequestMethod.PUT) 
  public Comment editeComment(@PathVariable String id , @RequestBody Comment c){ 
	  c.set_id(id); 
	  return  commentRepository.save(c); 
  } 
 }
 


