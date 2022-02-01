class AuthenticationService {
    AUTHENTICATION_KEY = "authenticationKey"
    storeUserSession(email) {
        sessionStorage.setItem(this.AUTHENTICATION_KEY, email);
    }

    isUserLoggedIn() {
        let email = this.getUserEmail();
        //console.log(email === null);
        if (email !== null) {

            return 'true'
        }
        return 'false';
    }

    removeUserSession() {
        sessionStorage.removeItem("authenticationKey");
    }

    getUserEmail() {
        return sessionStorage.getItem("authenticationKey");
    }
}

export default new AuthenticationService()