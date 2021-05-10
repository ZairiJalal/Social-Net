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
public class Comment {
	@Id
    private String _id;
    private String text;
    private Date date = new Date();
    private String userId;
    private String postId;
    private List<String>  likersList =new ArrayList<>();
	 
}
