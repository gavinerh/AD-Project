import axios from "axios";
import React, { Component } from "react";
import ArticlesService from "../service/ArticlesService";
import ArticleService from '../service/ArticlesService';
import AuthenticationService from "../service/AuthenticationService";



export default class CommentList extends Component{ 

    constructor(props) {
        super(props);
        this.retrievecomment = this.retrievecomment.bind(this);
        this.inputChangeHandler = this.inputChangeHandler.bind(this);
        this.submitCommentHandler = this.submitCommentHandler.bind(this);
        this.state = {
            comment: [],
            isLoading: true,
            errors: null,
            title: this.props.title,
            newComment: ''
        };
    }


    componentDidMount() {
        console.log("componentDidMount ran again");
        this.retrievecomment();
    }


    inputChangeHandler(event) {
        this.setState({
            newComment: event.target.value,
        })
        console.log(this.state.newComment);
    }

    submitCommentHandler() {
        ArticleService.makecomment(this.state.title, this.state.newComment,AuthenticationService.getUserEmail())
            .then(response => {
                this.setState({
                    comment: response.data,
                    newComment: '',
                    username :""
                })
                this.retrievecomment();
            });
        console.log("hihi");
    }

    retrievecomment() {
        let storedtitle = this.state.title;
        ArticleService.getcomment(storedtitle)
            .then(response => {
                this.setState({
                    comment: response.data,
                    isLoading: false,
                  
                });
                console.log(response);
            }

                // response.data.map(comment=>({
                //      commentcontent:`${comment.commentcontent}`,
                //      id:`${comment.id}`,
                //      ctitle:`${comment.title}`,

                // })
            )
            .catch(error => this.setState({ error, isLoading: false }));

    }


    render() {
        let isLoading = this.state.isLoading;
        return (
            <div className="list-group-item d-flex row">
                {!isLoading ? (
                    <div>
                        <h4>Comment List</h4>
                        {
                            this.state.comment.map(c => {
                                return (
                                    <ul>
                                      <li> {c.username}   :   {c.commentcontent}</li>   <div>{c.commenttime}</div>
                                    </ul>
                                )

                            })
                        }

                        <input type="text" onChange={this.inputChangeHandler} value={this.state.newComment} />
                        <button className="py-2 mb-2 btn btn-outline-danger rounded-4" onClick={this.submitCommentHandler}>Submit Comment</button>

                    </div>
                    // comment.map(comment => {
                    //     const { title, commentcontent } = comment;


                    //     return (
                    //         <div className="container g-3 flex-fill" >
                    //             <button className="py-2 mb-2 btn btn-outline-danger rounded-4" onClick={() => this.onsubmitCommentListener(title)}>
                    //                 comment
                    //             </button>
                    //             <ul>
                    //                 <li>{this.props.setDisplay}</li>
                    //                 <li>Someone:  {commentcontent}</li>
                    //             </ul>

                    //         </div>
                    //     );
                    // })
                ) : (
                    <p>Loading...</p>
                )}
            </div>
        );

    }



}