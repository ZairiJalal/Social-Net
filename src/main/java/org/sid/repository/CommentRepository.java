package org.sid.repository;

import org.sid.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


public interface CommentRepository extends MongoRepository<Comment, String>{

}
