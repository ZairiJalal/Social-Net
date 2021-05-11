package org.sid.entities;






import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data @NoArgsConstructor @AllArgsConstructor
public class AppRole {
	@Id
       private String _id;
       private String roleName;
}

