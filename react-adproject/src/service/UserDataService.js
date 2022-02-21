import axios from 'axios';
import AuthenticationService from './AuthenticationService';

class UserDataService{
    login(username, password){
        return axios.post(`http://localhost:8080/account/authenticate`, {
            email: username,
            password: password
        });
    }

    createUser(user) {
        return axios.post(`http://localhost:8080/account/register`, user)
    }

    getUser(){
        let email = AuthenticationService.getUserEmail();
        return axios.get(`http://localhost:8080/account/${email}`, AuthenticationService.setupHeader())
    }

    updateUser(name, phone, email, password){
        let user = {
            name: name,
            phone: phone,
            email: email,
            password: password
        }
        return axios.put(`http://localhost:8080/account/update`, user, AuthenticationService.setupHeader());
    }
}

export default new UserDataService();