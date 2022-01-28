package com.example.demo.newsApi;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import com.example.demo.model.apiModel.NewContent;
import com.example.demo.model.apiModel.NewsObject;
import com.fasterxml.jackson.databind.ObjectMapper;

// for NewsApi website api
public class NewsApi {
	// category can be example technology%20science (%20 is whitespace)
	
	public static NewsObject getNews(String category, String localdate, String key) {
		if(key == null) {
			key = "de30daafcb8d4b3e960359da9bd50fa2";
		}
	    String domain = "https://newsapi.org/v2";
	    String compulsoryCategory = "/everything";
	    String query = "?q=" + category;
	    if(localdate == null) {
	    	localdate = LocalDate.now().toString();
	    }
	    String date = "&from=" + localdate;
	    String sortBy = "&sortBy=popularity";
	    String apikey = "&apiKey=" + key;
	    String urlString = String.format("%s%s%s%s%s%s", domain, compulsoryCategory, query, date, sortBy, apikey);
	    try {
	    	return queryApi(urlString);
	    }catch(IOException e) {
	    	e.printStackTrace();
	    }catch(InterruptedException e) {
	    	e.printStackTrace();
	    }
	    return null;
	}
	
	private static NewsObject queryApi(String url) throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // parse JSON to Objects
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), NewsObject.class);
    }
}
