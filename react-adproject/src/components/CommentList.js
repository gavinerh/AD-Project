import axios from "axios";
import React, { Component } from "react";
import ArticlesService from "../service/ArticlesService";
import ArticleService from '../service/ArticlesService';
import AuthenticationService from "../service/AuthenticationService";
import './Sidebar.css';



export default class CommentList extends Component {

    constructor(props) {
        super(props);
        var symbol = AuthenticationService.getUserEmail();
        var pos = symbol.indexOf("@");
        var usernow = symbol.substring(0, pos);

        this.hidecomment = this.hidecomment.bind(this);
        this.retrievecomment = this.retrievecomment.bind(this);
        this.inputChangeHandler = this.inputChangeHandler.bind(this);
        this.submitCommentHandler = this.submitCommentHandler.bind(this);
        this.deletecomment = this.deletecomment.bind(this);
        this.state = {
            comment: [],
            isLoading: true,
            errors: null,
            title: this.props.title,
            newComment: '',
            usernow:usernow,
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
       if(this.state.newComment != ''){
        ArticleService.makecomment(this.state.title, this.state.newComment, AuthenticationService.getUserEmail())
            .then(response => {
                this.setState({
                    comment: response.data,
                    newComment: '',
                    username: "",
                    
                })
                this.retrievecomment();
            });
        console.log("hihi");}
    }


    deletecomment(content,time,name){
        
        
        
        
        var symbol = AuthenticationService.getUserEmail();
        var pos = symbol.indexOf("@");
        var username = symbol.substring(0, pos);


        if(name==username){
           

     //   if(name==username)
        ArticleService.deletecomment( this.state.title,  AuthenticationService.getUserEmail(),content,time)
        .then(response => {
            this.setState({
               
            })
            this.retrievecomment();
        });
          }
          else{
              alert("you cannot delete other's comment");

          }
          
        
        }
            

    hidecomment(id){
     var area = document.getElementById(id);
     area.style ="display:none";

    }


   

    retrievecomment() {
        let storedtitle = this.state.title;
        ArticleService.getcomment(storedtitle)
            .then(response => {
                this.setState({
                    comment: response.data,
                    isLoading: false,
                });
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

        <div className="whole">
            <div className="list-group-item d-flex row ">
                <svg xmlns="http://www.w3.org/2000/svg" className="bi bi-file-person-fill" viewBox="0 0 16 16" style={{ display: 'none' }}>
                    <symbol id="bi bi-file-person-fill" viewBox="0 0 16 16">
                        <path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zm-1 7a3 3 0 1 1-6 0 3 3 0 0 1 6 0zm-3 4c2.623 0 4.146.826 5 1.755V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1v-1.245C3.854 11.825 5.377 11 8 11z" />
                    </symbol >
                </svg>
                {!isLoading ? (
                    <div>
                        <h5>Comment List  </h5>
                       
                        {
                            this.state.comment.map(c => {
                                return (
                                    <ul className="licomment">
                                        <li className="licomment">
                                            <svg className="bi bi-file-person-fill" width="1em" height="1em">
                                                <use xlinkHref="#bi bi-file-person-fill" />
                                            </svg>

                                            {c.username}   :   {c.commentcontent}
                                            <button className="delete"  style={{ display:this.state.usernow==c.username? "block":"none" }} onClick={() => this.deletecomment(c.commentcontent,c.commenttime,c.username)}>delete</button>
                                            <div className="commenttime">{c.commenttime}</div>
                                            
                                            
                                            
                                            </li>

                                    </ul>
                                )

                            })
                        }

                                 <div>
                        <input className="inputcomment" type="text" onChange={this.inputChangeHandler} value={this.state.newComment} />
                        </div>
                      <div>  <button className="submitcomment" onClick={this.submitCommentHandler}>Submit Comment</button>
                       <button className="hidecomment" type="submit" onClick={() => this.hidecomment(this.state.title + "comment")}>
                           hide all</button></div>
                    </div>

                ) : (
                    <p>Loading...</p>
                )}
            </div>
            </div>
        );

    }



}