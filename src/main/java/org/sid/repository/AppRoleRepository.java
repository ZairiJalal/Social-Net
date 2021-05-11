package org.sid.repository;

import org.sid.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppRoleRepository extends MongoRepository<AppRole, String> {
	AppRole findByRoleName(String roleName);

}
