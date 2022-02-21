import React, { Component } from "react";
import ArticlesService from "../service/ArticlesService";
import ArticleService from '../service/ArticlesService';
import './ArticleList.css';
import noImage from '../assets/no-image-placeholder.svg';
import CommentList from "./CommentList";
import Modal from "./Modal";
import { Link, Redirect } from 'react-router-dom';
import AuthenticationService from "../service/AuthenticationService";
import { FacebookShareButton, TwitterShareButton, EmailShareButton } from "react-share";
import { FacebookIcon, TwitterIcon, EmailIcon } from "react-share";

export default class ArticleList extends Component {
    constructor(props) {
        super(props);
        this.retrieveArticles = this.retrieveArticles.bind(this);
        this.onLikeClickListener = this.onLikeClickListener.bind(this);
        this.onDislikeClickListener = this.onDislikeClickListener.bind(this);
        this.onCommentListener = this.onCommentListener.bind(this);
        this.checkArticleLike = this.checkArticleLike.bind(this);
        this.onBookmarkClickListener =this.onBookmarkClickListener.bind(this);

        this.state = {
            articles: [],
            comment: [],
            isLoading: true,
            errors: null,
            keyword: '',
            sortBy: '',
            displayComment: false,
            Likedd: false
           
        };
    }

    componentDidMount() {
        this.retrieveArticles();
    }

    onLikeClickListener(article) {
        console.log(article);
        ArticlesService.likeArticle(article);
    }

    onDislikeClickListener(article) {
        console.log(article);
        ArticleService.dislikeArticle(article);
    }

    onBookmarkClickListener(article){
        console.log(article);
        ArticlesService.bookmarkArticle(article);
    }

    checkArticleLike(article){
        console.log(article)
        ArticlesService.IsArticleLiked(article)
        .then(response=> console.log(response))
        
    }

    onsubmitCommentListener(title) {
        var comment = document.getElementById(title).value;
        this.setState({
            displayComment: title
        })
    }

    onCommentListener(id, title) {
        var area = document.getElementById(id);
        area.style = "display:block";
        this.setState((prevState) => ({
            displayComment: !prevState.displayComment
        }))

    }

    // set fnx to 'save' state of keyword
    keywordChangeHandler = (e) => {
        this.setState({
            keyword: e.target.value,
        })
    }

    //sorting articles handler
    sortByInputHandler = (e) => {
        this.setState({
            sortBy: e.target.value
        })
    }

    //on Submit use curr keyword to retrieve articles
    searchFormHandler = (e) => {
        e.preventDefault();
        ArticlesService.updateKeyword(this.state.keyword, this.state.sortBy)
            .then(response =>
                response.data.map(article => ({
                    sourceid: `${article.source.id}`,
                    id: `${article.id}`,
                    sourcename: `${article.source.name}`,
                    author: `${article.author}`,
                    title: `${article.title}`,
                    description: `${article.description}`,
                    url: `${article.url}`,
                    urlToImage: `${article.urlToImage}`,
                    prettytime: `${article.prettytime}`,

                 
                   

                }))
            )
            //change loading state to display data--> set active article
            .then(articles => {
                this.setState({
                    articles,
                    isLoading: false,
                    // keyword: '', //clear search field
                });
            })
            .then(response => {
                console.log("no error");
                // console.log(response);
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
                        isLoading: <p>Loading...</p>
                    })
                }
            });
    }

    //HOMEPAGE (TEMPORARY)
    retrieveArticles() {
        //using axios to request data
        ArticleService.getArticles()
            //once get response, map API endpoints to our props
            .then(response =>
                response.data.map(article => ({
                    sourceid: `${article.source.id}`,
                    id: `${article.id}`,
                    sourcename: `${article.source.name}`,
                    author: `${article.author}`,
                    title: `${article.title}`,
                    description: `${article.description}`,
                    url: `${article.url}`,
                    urlToImage: `${article.urlToImage}`,
                    prettytime: `${article.prettytime}`,
                    publishedAt: `${article.publishedAt}`,
                    isliked: `${article.isliked}`,
                    isdisliked: `${article.isdisliked}`,
                    isbookmarked: `${article.isbookmarked}`
                    
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
                    <div className='col-md-12 col-lg-9 border-0 flex-column'>
                        {!isLoading ? (
                            articles.map(article => {
                                const { sourceid, id, sourcename, title, description, url, urlToImage, prettytime, isliked,isdisliked,isbookmarked, publishedAt } = article;
                                
                                
                               
                                return (
                                       
                                    <div className="container px-3 pt-3" key={sourceid, id, title}>
                                        {/* article icons */}
                                        <svg xmlns="http://www.w3.org/2000/svg" style={{ display: 'none' }}>
                                            <symbol id="hand-thumbs-up" viewBox="0 0 16 16">
                                                <path fillRule="evenodd"
                                                    d="M8.864.046C7.908-.193 7.02.53 6.956 1.466c-.072 1.051-.23 2.016-.428 2.59-.125.36-.479 1.013-1.04 1.639-.557.623-1.282 1.178-2.131 1.41C2.685 7.288 2 7.87 2 8.72v4.001c0 .845.682 1.464 1.448 1.545 1.07.114 1.564.415 2.068.723l.048.03c.272.165.578.348.97.484.397.136.861.217 1.466.217h3.5c.937 0 1.599-.477 1.934-1.064a1.86 1.86 0 0 0 .254-.912c0-.152-.023-.312-.077-.464.201-.263.38-.578.488-.901.11-.33.172-.762.004-1.149.069-.13.12-.269.159-.403.077-.27.113-.568.113-.857 0-.288-.036-.585-.113-.856a2.144 2.144 0 0 0-.138-.362 1.9 1.9 0 0 0 .234-1.734c-.206-.592-.682-1.1-1.2-1.272-.847-.282-1.803-.276-2.516-.211a9.84 9.84 0 0 0-.443.05 9.365 9.365 0 0 0-.062-4.509A1.38 1.38 0 0 0 9.125.111L8.864.046zM11.5 14.721H8c-.51 0-.863-.069-1.14-.164-.281-.097-.506-.228-.776-.393l-.04-.024c-.555-.339-1.198-.731-2.49-.868-.333-.036-.554-.29-.554-.55V8.72c0-.254.226-.543.62-.65 1.095-.3 1.977-.996 2.614-1.708.635-.71 1.064-1.475 1.238-1.978.243-.7.407-1.768.482-2.85.025-.362.36-.594.667-.518l.262.066c.16.04.258.143.288.255a8.34 8.34 0 0 1-.145 4.725.5.5 0 0 0 .595.644l.003-.001.014-.003.058-.014a8.908 8.908 0 0 1 1.036-.157c.663-.06 1.457-.054 2.11.164.175.058.45.3.57.65.107.308.087.67-.266 1.022l-.353.353.353.354c.043.043.105.141.154.315.048.167.075.37.075.581 0 .212-.027.414-.075.582-.05.174-.111.272-.154.315l-.353.353.353.354c.047.047.109.177.005.488a2.224 2.224 0 0 1-.505.805l-.353.353.353.354c.006.005.041.05.041.17a.866.866 0 0 1-.121.416c-.165.288-.503.56-1.066.56z" />
                                            </symbol>

                                            <symbol id="hand-thumbs-down" viewBox="0 0 16 16">
                                                <path fillRule="evenodd"
                                                    d="M8.864 15.674c-.956.24-1.843-.484-1.908-1.42-.072-1.05-.23-2.015-.428-2.59-.125-.36-.479-1.012-1.04-1.638-.557-.624-1.282-1.179-2.131-1.41C2.685 8.432 2 7.85 2 7V3c0-.845.682-1.464 1.448-1.546 1.07-.113 1.564-.415 2.068-.723l.048-.029c.272-.166.578-.349.97-.484C6.931.08 7.395 0 8 0h3.5c.937 0 1.599.478 1.934 1.064.164.287.254.607.254.913 0 .152-.023.312-.077.464.201.262.38.577.488.9.11.33.172.762.004 1.15.069.13.12.268.159.403.077.27.113.567.113.856 0 .289-.036.586-.113.856-.035.12-.08.244-.138.363.394.571.418 1.2.234 1.733-.206.592-.682 1.1-1.2 1.272-.847.283-1.803.276-2.516.211a9.877 9.877 0 0 1-.443-.05 9.364 9.364 0 0 1-.062 4.51c-.138.508-.55.848-1.012.964l-.261.065zM11.5 1H8c-.51 0-.863.068-1.14.163-.281.097-.506.229-.776.393l-.04.025c-.555.338-1.198.73-2.49.868-.333.035-.554.29-.554.55V7c0 .255.226.543.62.65 1.095.3 1.977.997 2.614 1.709.635.71 1.064 1.475 1.238 1.977.243.7.407 1.768.482 2.85.025.362.36.595.667.518l.262-.065c.16-.04.258-.144.288-.255a8.34 8.34 0 0 0-.145-4.726.5.5 0 0 1 .595-.643h.003l.014.004.058.013a8.912 8.912 0 0 0 1.036.157c.663.06 1.457.054 2.11-.163.175-.059.45-.301.57-.651.107-.308.087-.67-.266-1.021L12.793 7l.353-.354c.043-.042.105-.14.154-.315.048-.167.075-.37.075-.581 0-.211-.027-.414-.075-.581-.05-.174-.111-.273-.154-.315l-.353-.354.353-.354c.047-.047.109-.176.005-.488a2.224 2.224 0 0 0-.505-.804l-.353-.354.353-.354c.006-.005.041-.05.041-.17a.866.866 0 0 0-.121-.415C12.4 1.272 12.063 1 11.5 1z" />
                                            </symbol>

                                            <symbol id="bookmark" viewBox="0 0 16 16">
                                                <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z" />
                                            </symbol>

                                            <symbol id="link" viewBox="0 0 16 16">
                                                <path
                                                    d="M6.354 5.5H4a3 3 0 0 0 0 6h3a3 3 0 0 0 2.83-4H9c-.086 0-.17.01-.25.031A2 2 0 0 1 7 10.5H4a2 2 0 1 1 0-4h1.535c.218-.376.495-.714.82-1z" />
                                                <path
                                                    d="M9 5.5a3 3 0 0 0-2.83 4h1.098A2 2 0 0 1 9 6.5h3a2 2 0 1 1 0 4h-1.535a4.02 4.02 0 0 1-.82 1H12a3 3 0 1 0 0-6H9z" />
                                            </symbol>

                                            <symbol id="comment" viewBox="0 0 16 16">
                                                <path fillRule="evenodd" d="M5 4a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1H5zm-.5 2.5A.5.5 0 0 1 5 6h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zM5 8a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1H5zm0 2a.5.5 0 0 0 0 1h3a.5.5 0 0 0 0-1H5z" />
                                                <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2zm10-1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1z" />
                                            </symbol>

                                            <symbol id="clock" viewBox="0 0 16 16">
                                                <path d="M8 3.5a.5.5 0 0 0-1 0V9a.5.5 0 0 0 .252.434l3.5 2a.5.5 0 0 0 .496-.868L8 8.71V3.5z" />
                                                <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm7-8A7 7 0 1 1 1 8a7 7 0 0 1 14 0z" />
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
                                                        <small className="opacity-75 py-1">{sourcename}&nbsp;&nbsp;
                                                            <svg className="bi " width="1em" height="1em">
                                                                <use xlinkHref="#clock" />
                                                            </svg>
                                                            &nbsp;{prettytime}
                                                        </small>
                                                        <p className="mb-0">{description}</p></a>
                                                    {/* buttons */}
                                                    <div className="d-flex justify-content-end mt-3">
                                                        <div className="me-1">
                                                            <button className="py-1 mb-1 btn btn-custom btn-sm btn-outline-dark" type="submit" onClick={() => this.onCommentListener(title + "comment", title)}>
                                                                <svg className="bi mx-1" width="1em" height="1em">
                                                                    <use xlinkHref="#comment" />
                                                                </svg>
                                                                Comment
                                                            </button>
                                                        </div>
                                                        <div className="me-1">
                                                            <button className="py-1 mb-1 btn btn-custom btn-sm btn-outline-dark" id="dropdown09" data-bs-toggle="dropdown"
                                                                aria-expanded="false"><svg className="bi mx-1" width="1em" height="1em">
                                                                    <use xlinkHref="#link" />
                                                                </svg>
                                                                Share</button>
                                                            <ul className="dropdown-menu" aria-labelledby="dropdown09">
                                                                <li className="dropdown-item"><FacebookShareButton url={url}
                                                                    quote={title}
                                                                    hashtag={"#newsbook"}
                                                                    description={title}
                                                                    className="Demo__some-network__share-button">
                                                                    <FacebookIcon size={24} round />
                                                                    <small>&nbsp;&nbsp;Share to Facebook</small>
                                                                </FacebookShareButton></li>
                                                                <li className="dropdown-item"><TwitterShareButton url={url}
                                                                    quote={title}
                                                                    hashtag={"#newsbook"}
                                                                    description={title}
                                                                    className="Demo__some-network__share-button">
                                                                    <TwitterIcon size={24} round />
                                                                    <small>&nbsp;&nbsp;Share to Twitter</small>
                                                                </TwitterShareButton></li>
                                                                <li className="dropdown-item"><EmailShareButton url={url}
                                                                    quote={title}
                                                                    hashtag={"#newsbook"}
                                                                    description={title}
                                                                    className="Demo__some-network__share-button">
                                                                    <EmailIcon size={24} round />
                                                                    <small>&nbsp;&nbsp;Email</small>
                                                                </EmailShareButton></li>
                                                            </ul>
                                                        </div>
                                                        
                                                        <div className="me-1">
                                                       
                                                            <button className={"py-1 mb-1 btn btn-custom btn-sm btn-outline-success" + (isliked==="true"? ' active':'')}type="submit" data-bs-toggle="button"
                                                                onClick={() => this.onLikeClickListener(article)}>
                                                                <svg className="bi mx-1" width="1em" height="1em">
                                                                    <use xlinkHref="#hand-thumbs-up" />
                                                                </svg>
                                                                Like
                                                            </button>
                                                            
                                                         
                                                        </div>
                                                        <div>
                                                            
                                                            
                                                            <button className={"py-1 mb-1 btn btn-custom btn-sm btn-outline-danger" + (isdisliked==="true"? ' active':'')} type="submit" data-bs-toggle="button" aria-pressed= 'false'
                                                                onClick={() => this.onDislikeClickListener(article)}>
                                                                <svg className="bi mx-1" width="1em" height="1em">
                                                                    <use xlinkHref="#hand-thumbs-down" />
                                                                </svg>
                                                                Dislike
                                                            </button>
                                                        
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div className="d-flex justify-content-end">
                                                <div>
                                                    <button className={"btn btn-custom btn-sm btn-outline-warning"+ (isbookmarked==="true"? ' active':'')} data-bs-toggle="button" type="submit" aria-pressed= "true" onClick={() => this.onBookmarkClickListener(article)}  >
                                                        <svg className="bi" width="1.5em" height="1.5em">
                                                            <use xlinkHref="#bookmark" />
                                                        </svg>
                                                    </button>
                                                </div>  
                                            </div>
                                        </div>
                                        <div id={title + "comment"} style={{ display: "none" }}>

                                            <CommentList title={title} >

                                            </CommentList>
                                        </div>

                                    </div>
                                );
                            })
                        ) : (
                            AuthenticationService.checkJwtValidity() ?
                            <p>Loading articles...</p>
                            : <Redirect to="/" />
                        )}
                    </div>

                    {/* Search bar */}
                    <div className='col-lg-3 d-none d-lg-block flex-column'>
                        <div className="container my-3">
                            <div className='p-3 card card-cover bg-light rounded-5 shadow-sm'>
                                <form onSubmit={this.searchFormHandler} >
                                    <input type="keyword" value={this.state.keyword} onChange={this.keywordChangeHandler}
                                        className="form-control mb-2" placeholder="Search..." aria-label="Search" />

                                    {/* sort by */}
                                    <hr />
                                    <h5>Sort by</h5>
                                    <div className="form-check" >
                                        <label>
                                            <input type="radio" name="popularity" value="popularity" className="form-check-input"
                                                checked={this.state.sortBy === "popularity"}
                                                onChange={this.sortByInputHandler} />
                                            Popularity
                                        </label>
                                    </div>
                                    <div className="form-check my-2" >
                                        <label>
                                            <input type="radio" name="publishedAt" value="publishedAt" className="form-check-input"
                                                checked={this.state.sortBy === "publishedAt"}
                                                onChange={this.sortByInputHandler} />
                                            Most recent
                                        </label>
                                    </div>
                                    <div className="form-check my-2" >
                                        <label>
                                            <input type="radio" name="relevancy" value="relevancy" className="form-check-input"
                                                checked={this.state.sortBy === "relevancy"}
                                                onChange={this.sortByInputHandler} />
                                            Relevance
                                        </label>
                                    </div>
                                    <button className="col-12 btn btn-secondary mt-2" type="submit">
                                        Submit
                                    </button>

                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}