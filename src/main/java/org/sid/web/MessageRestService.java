package org.sid.web;
import java.util.List; import java.util.Optional;

import org.sid.entities.Message;
import org.sid.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  
  @CrossOrigin(origins = "*", allowedHeaders = "*")
  
  public class MessageRestService {
  
  @Autowired private MessageRepository messageRepository; 
  
  @RequestMapping(value = "/messages", method = RequestMethod.GET) 
  public List<Message> getMessages(){ return messageRepository.findAll(); }
  
  
  @RequestMapping(value = "/messages/{id}", method = RequestMethod.GET) 
  public Optional<Message> getMessage(@PathVariable String id){ 
	  return messageRepository.findById(id);
  }
  
  @RequestMapping(value = "/messages", method = RequestMethod.POST) 
  public Message saveMessage(@RequestBody Message c){ 
	  return messageRepository.save(c); 
  }
  
  @RequestMapping(value = "/messages/{id}", method = RequestMethod.DELETE) 
  public boolean deleteMessage(@PathVariable String id){ 
	  messageRepository.deleteById(id);
      return true; 
  }
  
  @RequestMapping(value = "/messages/{id}", method = RequestMethod.PUT) 
  public Message editeMessage(@PathVariable String id , @RequestBody Message c){ 
	  c.setId(id); 
	  return  messageRepository.save(c); 
  } 
 }
 

