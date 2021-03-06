import ArticleService from '../service/ArticlesService';
import React, { Component, useEffect, useState } from 'react';
import Category from './Category';
import { Link, Redirect } from 'react-router-dom';
import AuthenticationService from '../service/AuthenticationService';
function UpdateCategory() {
    const [categories, setCategories] = useState([]);

    function formSubmitHandler(event) {
        event.preventDefault();
        console.log("code came here");
        ArticleService.setCategories(categories)
        .then(response => {
            if(response.status === 200){
                <Redirect to="/main" />
            }
        }).catch(error => console.log(error))
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
                    select: ischecked,
                };
                return updatedCategory;
            }
            return category;
        });
        setCategories(newCategoryList);
    }

    return (
        <div className="container p-3">
            { AuthenticationService.checkJwtValidity() ?
            <main>
                <div className="row g-5">
                    <div className="col-md-6">
                        <h4 className="mb-3">Select news categories</h4>
                        <form>
                            {categories.map(category1 => {
                                return <Category key={category1.name} category={category1} onInputCheckHandler={inputCheckHandler} />
                            })}
                            <button onClick={formSubmitHandler} className="mt-3 btn btn-primary" type="submit"><Link to="/main" style={{color: 'white', 'text-decoration': 'none'}}>Submit</Link></button>
                            {/* <input type='submit' /> */}
                        </form>
                    </div>
                </div>
            </main> : <Redirect to="/" />}
        </div>
        // <>
        // <div>
        // <h3>Please selected your favour categories:</h3>
        // </div>

        // <form onSubmit={formSubmitHandler}>
        //     {categories.map(category1 => {
        //         return <Category key={category1.name} category={category1} onInputCheckHandler={inputCheckHandler} />
        //     })}
        //     <input type='submit' />
        // </form>
        // </>
    )
}

export default UpdateCategory;
//hii