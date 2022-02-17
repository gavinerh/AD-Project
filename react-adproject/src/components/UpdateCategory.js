import ArticleService from '../service/ArticlesService';
import React, { Component, useEffect, useState } from 'react';
import Category from './Category';
import axios from 'axios';
function UpdateCategory() {
    const [categories, setCategories] = useState([]);

    function formSubmitHandler(event) {
        event.preventDefault();
        ArticleService.setCategories(categories);
    }

    useEffect(() => {
        ArticleService.getCategories()
            .then(response => {
                console.log(response);
                setCategories(response.data);
            });
    }, [])


    function inputCheckHandler(name, ischecked) {
        const newCategoryList = categories.map(category => {
            if (category.name === name) {
                const updatedCategory = {
                    ...category,
                    checked: ischecked,
                };
                return updatedCategory;
            }
            return category;
        });
        setCategories(newCategoryList);
    }

    return (
        <form onSubmit={formSubmitHandler}>
            {categories.map(category1 => {
                return <Category key={category1.name} category={category1} onInputCheckHandler={inputCheckHandler} />
            })}
            <input type='submit' />
        </form>
    )
}

export default UpdateCategory;
