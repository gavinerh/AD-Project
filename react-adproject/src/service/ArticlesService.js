import axios from "axios";
import AuthenticationService from "./AuthenticationService";

const ARTICLE_API_BASE_URL = "http://localhost:8080/newsapi/"

class ArticleDataService {



    getArticles() {
        return axios.get(ARTICLE_API_BASE_URL, AuthenticationService.setupHeader());
    }

    getBookmarkArticles() {
        return axios.get(`${ARTICLE_API_BASE_URL}bookmarked`, AuthenticationService.setupHeader());
    }

    getLikedArticles() {
        return axios.get(`${ARTICLE_API_BASE_URL}liked`, AuthenticationService.setupHeader());
    }

    getDislikedArticles() {
        return axios.get(`${ARTICLE_API_BASE_URL}disliked`, AuthenticationService.setupHeader());
    }

    findByCountryCategory(country, category) {
        return axios.get(ARTICLE_API_BASE_URL + "/" + country
            + "/" + category, AuthenticationService.setupHeader());
    }

    IsArticleLiked(article)
    {
        return axios.post(`${ARTICLE_API_BASE_URL}checkLike`, article ,AuthenticationService.setupHeader());
        
    }

    IsArticleDisliked(article)
    {
       return axios.get(`${ARTICLE_API_BASE_URL}checkDislike`, article ,AuthenticationService.setupHeader());
    }

    IsArticleBookmarked(article)
    {
        axios.get(`${ARTICLE_API_BASE_URL}checkBookmarked`, article ,AuthenticationService.setupHeader());

    }




    // updateKeyword(query) {
    //     console.log("Search called");
    //     var request = {
    //         params: {
    //             keyword: query
    //         }
    //     };
    //     return axios.get(ARTICLE_API_BASE_URL + "kw/updateKeyword", request);
    // }

    updateKeyword(query, sorting) {
        const instance = axios.create();
        instance.defaults.headers.common["Authorization"] = AuthenticationService.createJWTToken();

        let request = {
            params: {
                keyword: query,
                sortBy: sorting
            }
        }
        console.log(request)
        return instance.get(ARTICLE_API_BASE_URL + "kw/updateKeyword", request);
    }

    dislikeArticle(article) {
        axios.post(`${ARTICLE_API_BASE_URL}dislike`, article ,AuthenticationService.setupHeader());
    }


    likeArticle(article) {
       
        axios.post(`${ARTICLE_API_BASE_URL}like`, article ,AuthenticationService.setupHeader());
    }

    bookmarkArticle(article) {
        axios.post(`http://localhost:8080/newsapi/bookmark`, article, AuthenticationService.setupHeader());
    }


    makecomment(title,content,username){
        let comment ={
            title: title,
            commentcontent:content,
           username:username,
        }
        console.log(comment);

        return axios.post(`http://localhost:8080/comment`, comment,AuthenticationService.setupHeader());
    }


    getcomment(title) {
      let custom = {
          title:title,
         
      }
          
        return axios.post(`http://localhost:8080/getComment`,custom, AuthenticationService.setupHeader());
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