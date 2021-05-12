package org.sid.repository;

import java.util.List;

import org.sid.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface PostRepository extends MongoRepository<Post, String>{
	Post findBy_id(String _id);
	List<Post> findByUserId(String userId);

}
