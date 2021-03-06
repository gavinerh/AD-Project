import React, { Component } from "react";
import ArticleService from '../service/ArticlesService';
import './ArticleList.css';
import { Redirect } from "react-router-dom";
import noImage from '../assets/no-image-placeholder.svg';
import Modal from "./Modal";
import AuthenticationService from "../service/AuthenticationService";

export default class DislikedHistory extends Component {
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

    onDislikeClickListener(article) {
        console.log(article);
        ArticleService.dislikeArticle(article);
        window.location.reload(false);
    }

    retrieveArticles() {
        //using axios to request data
        ArticleService.getDislikedArticles()
            //once get response, map API endpoints to our props
            .then(response =>
                response.data.map(BookmarkedArticles => ({
                    id: `${BookmarkedArticles.id}`,
                    title: `${BookmarkedArticles.title}`,
                    description: `${BookmarkedArticles.description}`,
                    url: `${BookmarkedArticles.url}`,
                    imageurl: `${BookmarkedArticles.urlToImage}`,
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
                                const { id, sourcename, title, description, url, imageurl } = BookmarkedArticles;

                                return (
                                    <div className="container px-3 pt-3 rounded-5" key={id, url}>
                                        {/* article icons */}
                                        <svg xmlns="http://www.w3.org/2000/svg" style={{ display: 'none' }}>
                                            <symbol id="hand-thumbs-down" viewBox="0 0 16 16">
                                                <path fillRule="evenodd"
                                                    d="M8.864 15.674c-.956.24-1.843-.484-1.908-1.42-.072-1.05-.23-2.015-.428-2.59-.125-.36-.479-1.012-1.04-1.638-.557-.624-1.282-1.179-2.131-1.41C2.685 8.432 2 7.85 2 7V3c0-.845.682-1.464 1.448-1.546 1.07-.113 1.564-.415 2.068-.723l.048-.029c.272-.166.578-.349.97-.484C6.931.08 7.395 0 8 0h3.5c.937 0 1.599.478 1.934 1.064.164.287.254.607.254.913 0 .152-.023.312-.077.464.201.262.38.577.488.9.11.33.172.762.004 1.15.069.13.12.268.159.403.077.27.113.567.113.856 0 .289-.036.586-.113.856-.035.12-.08.244-.138.363.394.571.418 1.2.234 1.733-.206.592-.682 1.1-1.2 1.272-.847.283-1.803.276-2.516.211a9.877 9.877 0 0 1-.443-.05 9.364 9.364 0 0 1-.062 4.51c-.138.508-.55.848-1.012.964l-.261.065zM11.5 1H8c-.51 0-.863.068-1.14.163-.281.097-.506.229-.776.393l-.04.025c-.555.338-1.198.73-2.49.868-.333.035-.554.29-.554.55V7c0 .255.226.543.62.65 1.095.3 1.977.997 2.614 1.709.635.71 1.064 1.475 1.238 1.977.243.7.407 1.768.482 2.85.025.362.36.595.667.518l.262-.065c.16-.04.258-.144.288-.255a8.34 8.34 0 0 0-.145-4.726.5.5 0 0 1 .595-.643h.003l.014.004.058.013a8.912 8.912 0 0 0 1.036.157c.663.06 1.457.054 2.11-.163.175-.059.45-.301.57-.651.107-.308.087-.67-.266-1.021L12.793 7l.353-.354c.043-.042.105-.14.154-.315.048-.167.075-.37.075-.581 0-.211-.027-.414-.075-.581-.05-.174-.111-.273-.154-.315l-.353-.354.353-.354c.047-.047.109-.176.005-.488a2.224 2.224 0 0 0-.505-.804l-.353-.354.353-.354c.006-.005.041-.05.041-.17a.866.866 0 0 0-.121-.415C12.4 1.272 12.063 1 11.5 1z" />
                                            </symbol>
                                        </svg>

                                        {/*start of one news box*/}
                                        <div className="list-group-item rounded-5 d-flex gap-3 p-3" aria-current="true">
                                            {/* display image */}
                                            <div className="d-flex justify-content-start">
                                                <a href={url} target="_blank" rel="noopener noreferrer"><img src={imageurl} alt={title} width="128" height="128" className="rounded flex-shrink-0"
                                                    onError={(e) => { e.target.onerror = null; e.target.src = `${noImage}` }} /></a>
                                            </div>
                                            {/* display article text */}
                                            <div className="d-flex w-100 justify-content-between">
                                                <div className="flex-fill">
                                                    <a href={url} target="_blank" rel="noopener noreferrer" className="text-decoration-none text-dark"><h5 className="mb-0">{title}</h5>
                                                        <p className="py-1 mb-0">{description}</p></a>
                                                    {/* buttons */}
                                                    <div className="d-flex justify-content-end mt-3">
                                                        <div className="me-1">
                                                            <button className="py-1 mb-1 btn btn-custom btn-sm btn-outline-danger active" type="submit" data-bs-toggle="button" aria-pressed='false'
                                                                onClick={() => this.onDislikeClickListener(BookmarkedArticles)}>
                                                                <svg className="bi mx-1" width="1em" height="1em">
                                                                    <use xlinkHref="#hand-thumbs-down" />
                                                                </svg>
                                                                Dislike
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                );
                            })
                        ) : (
                            AuthenticationService.checkJwtValidity() ?
                            <p>Loading articles...</p> : <Redirect to="/" />
                        )}
                    </div>
                </div>
            </React.Fragment>
        );
    }
}