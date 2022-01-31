package com.news.app.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.app.model.Articles;
import com.news.app.model.NewsSet;
import com.news.app.model.Source;

@Service
public class NewsService {
	
	//HOME PAGE returning all latest articles on technology
	public static NewsSet getNewsHome(String category, String localdate, String key) {
		if(key == null) {
			key = "a660825b855545d1971d84a7af17d393";
		}
	    String domain = "https://newsapi.org/v2/everything";
	    String query = "?q=" + category;
	    if(localdate == null) {
	    	LocalDate temp = LocalDate.now();
	    	localdate = temp.minusDays(7).toString();
	    }
	    String date = "&from=" + localdate;
	    String sortBy = "&sortBy=popularity";
	    String apikey = "&apiKey=" + key;
	    String urlString = String.format("%s%s%s%s%s", domain, 
	    		query, date, sortBy, apikey); 
		    try {
		    	return queryApi(urlString);    
		    }catch(IOException e) {
		    	e.printStackTrace();
		    }catch(InterruptedException e) {
		    	e.printStackTrace();
		    }
	    return null;
	}
	
	//By selecting COUNTRY or CATEGORY
	public static NewsSet getNewsByCountryCategory(String country, String category, String key) {
		if(key == null) {
			key = "a660825b855545d1971d84a7af17d393";
		}		
	    String urlString = "https://newsapi.org/v2/top-headlines"+"?country="+country+
		    		"&category=" + category+"&apiKey=" + key;
		    try {
		    	return queryApi(urlString);    	
		    }catch(IOException e) {
		    	e.printStackTrace();
		    }catch(InterruptedException e) {
		    	e.printStackTrace();
		    }
	    return null;
	}
	
	//by using search bar--> at the moment only one word 
	public static NewsSet getNewsByKeyword(String keyword, String key) {
		if(key == null) {
			key = "a660825b855545d1971d84a7af17d393";
		}
	    String urlString = "https://newsapi.org/v2/everything?q=" + keyword+
		    		"&sortBy=relevancy"+"&apiKey=" + key;
		    try {
		    	return queryApi(urlString);    	
		    }catch(IOException e) {
		    	e.printStackTrace();
		    }catch(InterruptedException e) {
		    	e.printStackTrace();
		    }
	    return null;
	}
	
	//by using source
//	public static NewsSet getNewsBySource(String source1, String key) {
//		if(key == null) {
//			key = "a660825b855545d1971d84a7af17d393";
//		}
//	    String urlString = "https://newsapi.org/v2/top-headlines?sources=" + source1+
//	    		"&apiKey=" + key;
//		    try {
//		    	return queryApi(urlString);    	
//		    }catch(IOException e) {
//		    	e.printStackTrace();
//		    }catch(InterruptedException e) {
//		    	e.printStackTrace();
//		    }
//	    return null;
//	}
	
	private static NewsSet queryApi(String url) throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // parse JSON to Objects
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), NewsSet.class);
    }
}