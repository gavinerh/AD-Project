import axios from "axios";

const ARTICLE_API_BASE_URL = "http://localhost:8080/newsapi/"

class ArticleDataService {
    getArticles() {
        return axios.get(ARTICLE_API_BASE_URL);
    }
    findByCountryCategory(country, category) {
        return axios.get(ARTICLE_API_BASE_URL+"/"+ country 
            +"/"+category);
    }
   updateKeyword(query){
        console.log("Search called");
        var request = {
            params: {
                keyword: query
            }
        }
        return axios.get(ARTICLE_API_BASE_URL+"kw/updateKeyword", request);
    }
    dislikeArticle(article){
        axios.post(`${ARTICLE_API_BASE_URL}like`, article);
    }

    likeArticle(article){
        axios.post(`${ARTICLE_API_BASE_URL}dislike`, article);
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