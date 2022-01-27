class AuthenticationService{
    AUTHENTICATION_KEY = "authenticationKey"
    storeUserSession(email){
        sessionStorage.setItem(this.AUTHENTICATION_KEY, email);
    }

    isUserLoggedIn(){
        let email = sessionStorage.getItem(this.AUTHENTICATION_KEY);
        console.log(email === null);
        if(email !== null){

            return 'true'
        }
        return 'false';
    }

    removeUserSession(){
        sessionStorage.removeItem(this.AUTHENTICATION_KEY);
    }
}

export default new AuthenticationService()