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

    // register(name, phone, email, password){
    //     return axios.post('http://localhost:8080:/account/register', {
    //         name: name,
    //         phone: phone,
    //         email: email,
    //         password: password
    //     });
    // }

    getUser(){
        let email = AuthenticationService.getUserEmail();
        return axios.get(`http://localhost:8080/account/${email}`)
    }

    updateUser(name, phone, email, password){
        return axios.put(`http://localhost:8080/account/update`, {
            name: name,
            phone: phone,
            email: email,
            password: password
        });
    }
}

export default new UserDataService();