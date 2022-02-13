package com.example.demo.model;




import java.text.SimpleDateFormat;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Comment {
	
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	private String commentcontent;
    // @ManyToOne(fetch=FetchType.EAGER)
	private String username;
    private String title;
    private String commenttime;
    
	

    
    
	
	

}
