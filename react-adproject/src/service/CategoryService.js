import axios from 'axios';
import AuthenticationService from './AuthenticationService';

export const getCategories = () => {
    return axios.get("http://localhost:8080/category", AuthenticationService.setupHeader());
};

export const createCategory = (name) => {
    return axios.post('http://localhost:8080/category', { name, isChecked: false }, AuthenticationService.setupHeader());
}

export const deleteCategory = (id) => {
    return axios.delete(`http://localhost:8080/category/${id}`, AuthenticationService.setupHeader());
}