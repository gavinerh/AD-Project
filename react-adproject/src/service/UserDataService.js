import axios from 'axios';

class UserDataService{
    login(username, password){
        return axios.post(`http://localhost:8080/account/login`, {
            email: username,
            password: password
        });
    }

    register(name, phone, email, password){
        return axios.post('http://localhost:8080:/account/register', {
            name: name,
            phone: phone,
            email: email,
            password: password
        });
    }
}

export default new UserDataService();