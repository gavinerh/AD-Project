import React, { Component } from "react";
import ArticlesService from "../service/ArticlesService";
import ArticleService from '../service/ArticlesService';
import './ArticleList.css';
import noImage from '../assets/no-image-placeholder.svg';
import Modal from "./Modal";
import AuthenticationService from "../service/AuthenticationService";

export default class BookmarkList extends Component {
    constructor(props) {
        super(props);
        this.retrieveArticles = this.retrieveArticles.bind(this);

        this.state = {
            articles: [],
            isLoading: true,
            errors: null
        };
    }

    componentDidMount() {
        this.retrieveArticles();
    }

    onBookmarkClickListener(article) {
        console.log(article);
        ArticlesService.bookmarkArticle(article);
        window.location.reload(false);
    }

    retrieveArticles() {
        //using axios to request data
        ArticleService.getBookmarkArticles()
            //once get response, map API endpoints to our props
            .then(response =>
                response.data.map(BookmarkedArticles => ({
                    id: `${BookmarkedArticles.id}`,
                    title: `${BookmarkedArticles.title}`,
                    description: `${BookmarkedArticles.description}`,
                    url: `${BookmarkedArticles.url}`,
                    urlToImage: `${BookmarkedArticles.urlToImage}`,
                }))
            )
            //change loading state to display data--> set active article
            .then(articles => {
                this.setState({
                    articles,
                    isLoading: false
                });
            })
            .catch(error => {
                if (!AuthenticationService.checkJwtValidity()) {
                    this.setState({
                        isLoading: <div>
                            <Modal />
                        </div>
                    })
                } else {
                    this.setState({
                        isLoading: <p>Loading Articles...</p>
                    })
                }
            });
    }

    render() {
        const { isLoading, articles } = this.state;
        return (
            <React.Fragment>
                <div className="d-flex">
                    {/* Articles column */}
                    <div className='col-sm-12 col-lg-9 border-0 flex-column'>
                        {!isLoading ? (
                            articles.map(BookmarkedArticles => {
                                const { id, sourcename, title, description, url, urlToImage } = BookmarkedArticles;

                                return (
                                    <div className="container px-3 pt-3" key={id, url}>
                                        {/* article icons */}
                                        <svg xmlns="http://www.w3.org/2000/svg" style={{ display: 'none' }}>
                                            <symbol id="bookmark" viewBox="0 0 16 16">
                                                <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z" />
                                            </symbol>
                                        </svg>


                                        {/*start of one news box*/}
                                        <div className="list-group-item rounded-5 d-flex gap-3 p-3" aria-current="true">
                                            {/* display image */}
                                            <div className="d-flex justify-content-start">
                                                <a href={url} target="_blank" rel="noopener noreferrer"><img src={urlToImage} alt={title} width="128" height="128" className="rounded flex-shrink-0"
                                                    onError={(e) => { e.target.onerror = null; e.target.src = `${noImage}` }} /></a>
                                            </div>
                                            {/* display article text */}
                                            <div className="d-flex w-100 justify-content-between">
                                                <div className="flex-fill">
                                                    <a href={url} target="_blank" rel="noopener noreferrer" className="text-decoration-none text-dark"><h5 className="mb-0">{title}</h5>
                                                        <p className="py-1 mb-0">{description}</p></a>
                                                </div>
                                            </div>
                                            <div className="d-flex justify-content-end">
                                                <div>
                                                    <button className="btn btn-custom btn-sm btn-outline-warning active" data-bs-toggle="button" type="submit" aria-pressed="true" onClick={() => this.onBookmarkClickListener(BookmarkedArticles)}  >
                                                        <svg className="bi" width="1.5em" height="1.5em">
                                                            <use xlinkHref="#bookmark" />
                                                        </svg>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                );
                            })
                        ) : (
                            <p>Loading articles...</p>
                        )}
                    </div>
                </div>
            </React.Fragment>
        );
    }
}