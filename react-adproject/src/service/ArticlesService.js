import axios from "axios";
import AuthenticationService from "./AuthenticationService";

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
        };
        return axios.get(ARTICLE_API_BASE_URL+"kw/updateKeyword", request);
    }


// updateKeyword(query){
//     console.log("Search called");
//     var request = {
//         params: {
//             keyword: query
//         }
//     };
//     return axios.get("http://localhost:8080/search", request);
// }
    dislikeArticle(article){
        let customArticle = {
            title: article.title,
            description: article.description,
            url: article.url,
        }
        axios.post(`http://localhost:8080/dislike`, customArticle)
        .catch(error => console.log(error));
    }


    likeArticle(article){
        let customArticle = {
            title: article.title,
            description: article.description,
            url: article.url,
        }
        axios.post(`http://localhost:8080/like`, customArticle);
    }

    makecomment(title,content){
        let comment ={
            title: title,
            commentcontent:content,
           
        }



         

       return  axios.post(`http://localhost:8080/comment`, comment);
    }


    getcomment(title) {
      let custom = {
          title:title,
         
      }
          
        return axios.post(`http://localhost:8080/getComment`,custom);
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