package org.sid.entities;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data @AllArgsConstructor @NoArgsConstructor
public class Message {
	@Id
    private String id;
    private Integer senderId;
    private Integer reseverId; 
    private String date;
    private String text;

}
