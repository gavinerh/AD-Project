import axios from "axios";

class AuthenticationService {
    AUTHENTICATION_KEY = "authenticationKey"
    TOKEN_KEY = "jwtToken";
    storeUserSession(email) {
        sessionStorage.setItem(this.AUTHENTICATION_KEY, email);
    }

    removeJwtToken(){
        sessionStorage.removeItem(this.TOKEN_KEY);
    }

    isUserLoggedIn() {
        let email = this.getUserEmail();
        //console.log(email === null);
        if (email !== null) {

            return 'true'
        }
        return 'false';
    }

    registerSuccessfulLogin(token){
        sessionStorage.setItem(this.TOKEN_KEY, token);
        this.setupHeader(this.createJWTToken(token));
    }

    createJWTToken(){
        let token = sessionStorage.getItem(this.TOKEN_KEY);
        return "Bearer " + token;
    }

    setupHeader(){
        let token = this.createJWTToken();
        return {headers: {
            "Authorization": token
        }};
    }

    // setupAxiosInterceptors(token){
    //     axios.interceptors.request.use(
    //         (config) => {
    //             if(this.isUserLoggedIn()){
    //                 config.headers.authorization = token
    //             }
    //             return config;
    //         }
    //     )
    // }

    removeUserSession() {
        sessionStorage.removeItem("authenticationKey");
        this.removeJwtToken();
    }

    getUserEmail() {
        return sessionStorage.getItem("authenticationKey");
    }

    checkTokenPresent(){
        let token = sessionStorage.getItem(this.TOKEN_KEY);
        console.log(token === null);
        if(token !== null){
            return true;
        }else{
            return false;
        }
    }

    checkJwtValidity(){
        let token = sessionStorage.getItem(this.TOKEN_KEY);
        let decodedObject = JSON.parse(atob(token.split('.')[1]));
        console.log(Date.now()/1000);
        console.log(decodedObject);
        if(decodedObject.exp < Date.now()/1000){
            
            return false;
        }
        console.log("token is still valid");
        return true;
    }
}

export default new AuthenticationService()