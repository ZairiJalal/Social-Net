package org.sid.repository;

import java.util.Optional;

import org.sid.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AppUserRepository extends MongoRepository<AppUser, String >{	
	AppUser findByUsername(String username);
	AppUser findBy_id(String _id);
	AppUser findByEmail(String email);
	
	
}
