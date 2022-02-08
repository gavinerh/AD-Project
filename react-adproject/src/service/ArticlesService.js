import axios from "axios";
import AuthenticationService from "./AuthenticationService";

const ARTICLE_API_BASE_URL = "http://localhost:8080/newsapi/"

class ArticleDataService {
    getArticles() {
        return axios.get(ARTICLE_API_BASE_URL, AuthenticationService.setupHeader());
    }
    findByCountryCategory(country, category) {
        return axios.get(ARTICLE_API_BASE_URL+"/"+ country 
            +"/"+category, AuthenticationService.setupHeader());
    }
    findByKeyword(keyword) {
        console.log('search axios is called');
        return axios.get(ARTICLE_API_BASE_URL+"/kw/"+keyword, AuthenticationService.setupHeader());
    }

    dislikeArticle(article){
        let customArticle = {
            title: article.title,
            description: article.description,
            url: article.url,
        }
        axios.post(`http://localhost:8080/dislike`, customArticle, AuthenticationService.setupHeader())
        .catch(error => console.log(error));
    }

    likeArticle(article){
        let customArticle = {
            title: article.title,
            description: article.description,
            url: article.url,
        }
        axios.post(`http://localhost:8080/like`, customArticle, AuthenticationService.setupHeader());
    }

    practiceSearch(query){
        console.log("practice search called");
        var request = {
            params: {
                keyword: query
            }
        }
        return axios.get("http://localhost:8080/practice/search", request);
    }
}
export default new ArticleDataService();