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
    findByKeyword(keyword) {
        return axios.get(ARTICLE_API_BASE_URL+"/kw/"+keyword);
    }
}
export default new ArticleDataService();