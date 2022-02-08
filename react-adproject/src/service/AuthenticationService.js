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

    createJWTToken(token){
        return "Bearer " + token;
    }

    setupHeader(){
        let token = sessionStorage.getItem(this.TOKEN_KEY);
        token = this.createJWTToken(token);
        return {headers: {"Authorization": token}};
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
    }

    getUserEmail() {
        return sessionStorage.getItem("authenticationKey");
    }
}

export default new AuthenticationService()