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
	private List<String> likedNews=new ArrayList<>();
	private List<Integer> result=new ArrayList<>();
	
    @Override
    public String toString() {
   
    	return titles.toString()+" "+likedNews.toString()+" "+result;
    }

}
