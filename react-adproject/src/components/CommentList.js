import axios from "axios";
import React, { Component } from "react";
import ArticlesService from "../service/ArticlesService";
import ArticleService from '../service/ArticlesService';
import AuthenticationService from "../service/AuthenticationService";



export default class CommentList extends Component{ 

    constructor(props) {
        super(props);
       
        this.retrievecomment = this.retrievecomment.bind(this);
       
        
       
        this.state = {
           
            comment:[],
            isLoading: true,
            errors: null,
           
        };
    }


    componentDidMount() {
        this.retrievecomment(this.props.title);
    }



    


    retrievecomment(title){
        
        ArticleService.getcomment(title)
        .then(response =>
           
            response.data.map(comment=>({
                 commentcontent:`${comment.commentcontent}`,
                 id:`${comment.id}`,
                 ctitle:`${comment.title}`,

            }))
            
            )
            .then(comment => {
                this.setState({
                    comment,
                    isLoading:false,
                });
            })
            .catch(error => this.setState({ error, isLoading: false }));

    }


    render() {
        const { isLoading, comment } = this.state;
        return (
            <React.Fragment>
                <div className="list-group-item d-flex row">
                    {!isLoading ? (
                        comment.map(comment => {
                            const { title,commentcontent  } = comment;
                            

                            return (
                                <div className="container g-3 flex-fill" >
                                    <ul>
                                       
                                        <li>Someone:  {commentcontent}</li>
                                    </ul>
                                   

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