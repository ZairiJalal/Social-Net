package org.sid.entities;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data @AllArgsConstructor @NoArgsConstructor
public class Post {
	
	@Id
    private String _id;
    private String descripton;
    private String picture;
    private Date date = new Date();
    private List<String> likes = new ArrayList<>();
	private String userId;



}