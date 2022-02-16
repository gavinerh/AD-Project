import axios from "axios";
import AuthenticationService from "./AuthenticationService";

const ARTICLE_API_BASE_URL = "http://localhost:8080/newsapi/"

class ArticleDataService {



    getArticles() {
        return axios.get(ARTICLE_API_BASE_URL, AuthenticationService.setupHeader());
    }

    findByCountryCategory(country, category) {
        return axios.get(ARTICLE_API_BASE_URL + "/" + country
            + "/" + category, AuthenticationService.setupHeader());
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
        let customArticle = {
            title: article.title,
            description: article.description,
            url: article.url,
            publishedAt:'2022-02-14T11:01:15Z',
             
        }
        axios.post(`http://localhost:8080/newsapi/dislike`, customArticle, AuthenticationService.setupHeader())
            .catch(error => console.log(error));
    }


    likeArticle(article) {
        let customArticle = {
            title: article.title,
            description: article.description,
            url: article.url,
            publishedAt: '2022-02-14T11:01:15Z',
        }
        axios.post(`http://localhost:8080/newsapi/like`, customArticle, AuthenticationService.setupHeader());
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


    deletecomment(title,username,content,commenttime){
       let decomment={
        title: title,
        commentcontent:content,
       username:username,
       commenttime:commenttime,
       }
         

        return axios.post(`http://localhost:8080/deletecomment`, decomment,AuthenticationService.setupHeader());
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