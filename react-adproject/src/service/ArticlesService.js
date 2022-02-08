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
<<<<<<< HEAD
    findByKeyword(keyword) {
        console.log('search axios is called');
        return axios.get(ARTICLE_API_BASE_URL+"/kw/"+keyword, AuthenticationService.setupHeader());
=======
   updateKeyword(query){
        console.log("Search called");
        var request = {
            params: {
                keyword: query
            }
        }
        return axios.get(ARTICLE_API_BASE_URL+"kw/updateKeyword", request);
>>>>>>> ef889f43c2b692a61af9585be9c05694e074547f
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

//    practiceSearch(query){
//        console.log("practice search called");
//        var request = {
//            params: {
//                keyword: query
//            }
//        }
//        return axios.get("http://localhost:8080/practice/search", request);
//    }
}
export default new ArticleDataService();