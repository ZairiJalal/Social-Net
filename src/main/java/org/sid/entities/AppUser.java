package org.sid.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data @AllArgsConstructor @NoArgsConstructor
public class AppUser {
	
	@Id
    private String _id;
    private String picture;
    private String coverpicture;
    private String username;
    private String password;
    private String email;
    private String birth;
    private String description;
    private String gender;
    private String location;
    private String school;
    private String job;
    private List<String> followersList=new ArrayList<>();
    private List<String> followingsList=new ArrayList<>();
    private Collection<AppRole> appRoles=new ArrayList<>();


	
}
