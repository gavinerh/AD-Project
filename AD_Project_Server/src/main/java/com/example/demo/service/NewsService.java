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
	
	private static final String myKey = "&apiKey=fbbc757feb5b441b805c38dc2ad94bd3";
	private static final String myLang = "&language=en";
	private static final String myPageSize = "&pageSize=30";
	
	//HOME PAGE returning all latest articles on technology
	public static NewsSet getNewsHome(String category, String localdate, String key) {
		if(key == null) {
			key = myKey;
		}
	    
	    if(localdate == null) {
//	    	LocalDate temp = LocalDate.now();
//	    	localdate = temp.minusDays(3).toString();
	    	localdate=LocalDate.now().toString(); //retrieve latest news
	    }
	    
	    String domain = "https://newsapi.org/v2/everything";
	    String query = "?q=" + category;
	    String date = "&from=" + localdate;
	    String sortBy = "&sortBy=relevancy";
	    String urlString = domain + query + date + sortBy + myLang + myPageSize + key; 
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

		//String key = myKey;
		String key = "&apiKey=fbbc757feb5b441b805c38dc2ad94bd3";
		
		if(country == null) {
			country ="";
		}
		LocalDate temp = LocalDate.now();
    	String localdate = temp.toString();
    	String date = "&from=" + localdate;
    	String pagesize = "&pageSize=30";
	    String sortBy = "&sortBy=popularity";
	    String language = "&language=en";
	    String urlString = "https://newsapi.org/v2/top-headlines"+
		    		"?category=" + category+sortBy+language+pagesize+key;

//	    String urlString = "https://newsapi.org/v2/top-headlines"+
//		    		"?category=" + category+sortBy+language+pagesize+"&apiKey=" + key;

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
	
	public static NewsSet getNewsByKeyword(String keyword, String sortBy, String localdate, String key) {
		if(key == null) {
			key = myKey;
		}
		
		if(sortBy == null) {
			sortBy = "relevance";
		}
		
		if(localdate == null) {
			localdate=LocalDate.now().toString();
		}
		
		// to search for multiple words
		String newkeyword = ReplaceSpace(keyword);

		String urlString = "https://newsapi.org/v2/everything?q=" + newkeyword + 
				"&sortBy=" + sortBy + "&from=" + localdate + myLang + myPageSize + key;
		try {
			return queryApi(urlString);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
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
