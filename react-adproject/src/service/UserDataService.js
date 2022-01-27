import axios from 'axios';

class UserDataService{
    login(username, password){
        return axios.post(`http://localhost:8080/users/username=${username}&password=${password}`);
    }

    register(username, password, phone, email){
        
    }
}

export default new UserDataService();