import React, { Component } from "react";
import ArticleService from '../service/ArticlesService';

export default class ArticleList extends Component {
     // constructor
    constructor(props) {
        super(props);
        this.retrieveArticles = this.retrieveArticles.bind(this);

        this.state = {
            articles:[],
            isLoading:true,
            errors:null
        };
    }

    componentDidMount() {
        this.retrieveArticles();
    }

    retrieveArticles() {
        //using axios to request data
        ArticleService.getArticles()
        //once get response, map API endpoints to our props
        .then(response => 
            response.data.map(article => ({
                sourceid: `${article.source.sourceid}`,
                id: `${article.source.id}`,
                sourcename: `${article.source.name}`,
                author: `${article.author}`,
                title: `${article.title}`,
                description: `${article.description}`,
                url: `${article.url}`,
                imageurl:`${article.urlToImage}`,
                content:`${article.content}`
            }))
        )
        //change loading state to display data--> set active article
        .then(articles => {
            this.setState({
                articles,
                isLoading: false
            });
        })
        .catch(error => this.setState({error, isLoading: false}));
    }

    render() {
        const { isLoading, articles } = this.state;
        return (
             <React.Fragment>
                <h2>Articles List</h2> 
                  <div> 
                      {!isLoading ? ( 
                        articles.map(article => {
                            const { sourceid, id, sourcename, title, description, url, imageurl, content } = article;
                            return (
                                <div key={sourceid, id, title}>
                                    <h4>{title}</h4>
                                    <p>{sourcename}</p>
                                    <p>{description}</p>
                                    <div><img src={imageurl} alt={title} width="200" height="200" /></div>
                                    {/* <p>{imageurl}</p> */}
                                    <p>{content}</p>
                                    <p><a href={url}>Click here to go to webpage</a></p>
                                    <hr />
                                </div>
                            );
                        })
                        ) : (
                            <p>Loading...</p>
                    )}
                </div>
             </React.Fragment> 
        );
    }
}