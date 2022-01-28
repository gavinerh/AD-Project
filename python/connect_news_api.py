import datetime

import requests;
import NewsObject;

key = "de30daafcb8d4b3e960359da9bd50fa2";
domain = "https://newsapi.org/v2";
category = "/everything";
extraQuery = "technology%20science";
query = "?q=" + extraQuery;
date = "&from=" + datetime.datetime.now().strftime("%Y-%m-%d")
sortBy = "&sortBy=popularity";
apikey = "&apiKey=" + key;

url = domain + category + query + date + sortBy + apikey
response = requests.get(url)
# print(response.json())
jsonData = response.json()


