package com.example.demo.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.demo.model.Articles;
import com.example.demo.model.NewsSet;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NewsService {
	
	//HOME PAGE returning all latest articles on technology
	public static NewsSet getNewsHome(String category, String localdate, String key) {
		if(key == null) {
			key = "de30daafcb8d4b3e960359da9bd50fa2";
		}
	    String domain = "https://newsapi.org/v2/everything";
	    String query = "?q=" + category;
	    if(localdate == null) {
//	    	LocalDate temp = LocalDate.now();
//	    	localdate = temp.minusDays(7).toString();
	    	localdate=LocalDate.now().toString(); //retrieve latest news
	    }
	    String date = "&from=" + localdate;
	    String sortBy = "&sortBy=publishedAt"; //"&sortBy=popularity"; 
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
	public static ArrayList<Articles> getNewsByCountryCategory(String category, String country) {
		String key = "a660825b855545d1971d84a7af17d393";
		
		if(country == null) {
			country ="";
		}
		LocalDate temp = LocalDate.now();
    	String localdate = temp.toString();
    	String date = "&from=" + localdate;
    	String pagesize = "&pageSize=30";
	    String sortBy = "&sortBy=popularity";
	    String language = "&language=en";
	    String urlString = "https://newsapi.org/v2/top-headlines"+"?country="+country+
		    		"&category=" + category+date+sortBy+language+pagesize+"&apiKey=" + key;
		    try {
		    	NewsSet ns1 = queryApi(urlString);
	    return ns1.getArticles();    	
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
		
		//to search for multiple words
		String newkeyword = ReplaceSpace(keyword);
				
	    String urlString = "https://newsapi.org/v2/everything?q=" + newkeyword+
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
	static String ReplaceSpace(String s) {
		String plus="+";
		return s.replace(" ", plus); 
	}
}
