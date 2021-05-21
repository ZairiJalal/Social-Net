package org.sid.entities;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.websocket.Decoder.Binary;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data @AllArgsConstructor @NoArgsConstructor
public class Post {
	
	@Id
    private String _id;
    private String description;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private byte[]  picture;
   	private String  pictureUrl;
    private Date date = new Date();
    private List<String> likes = new ArrayList<>();
	private String userId;



}