package com.example.demo.model.JsonModel;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

// send json data to python
@Data
@NoArgsConstructor
public class MLJson {
	private List<String> titles=new ArrayList<>();
	private List<String> likeDislike=new ArrayList<>();
	private String like;
	private String dislike;
	private String result;
	
    @Override
    public String toString() {
    	String s = "";
    	return s = titles.toString()+" "+likeDislike.toString()+" "+like+" "+dislike+" "+result;
    }

}
