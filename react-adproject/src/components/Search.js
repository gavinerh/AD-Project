import React, { Component } from "react";
import { BrowserRouter as Router, Link } from "react-router-dom";
import ArticlesService from "../service/ArticlesService";
import ArticleList from "./ArticleList";

export default class Search extends Component {
    constructor(props) {
        super(props);
        this.onChangeCountry = this.onChangeCountry.bind(this);
        this.onChangeCategory = this.onChangeCategory.bind(this);
        this.onChangeKeywords = this.onChangeKeywords.bind(this);
        this.saveSearch = this.saveSearch.bind(this);
        this.newSearch = this.newSearch.bind(this);

        //empty state
        this.state = {        
            country: '',
            category: '',
            keywords: ''
        };
    }

    onChangeCountry(e) {
        this.setState({
            //country is property
            country:e.target.value
        });
    }
    onChangeCategory(e) {
        this.setState({
            //category is property
            category:e.target.value
        });
    }
    onChangeKeywords(e) {
        this.setState({
            //keywords is property
            keywords:e.target.value
        });
    }
    
    saveSearch() {
        var data ={
            country:this.state.country,
            category:this.state.category,
            keywords: this.state.keywords
        };

        ArticlesService.findByCountryCategory(data)
          .then(response => {
            this.setState({
                id: response.data.id,
                country:response.data.country,
                category:response.data.category,
                keywords:response.data.keywords
            });
            console.log(response.data);
          })
          .catch(e => {
          console.log(e);
          });
    }

    newSearch() {
        this.setState({
            id: null,
            country: '',
            category: '',
            keywords: ''
        });
    }

    render() {
        return (
            <div className="submit-form">
            {this.state.submitted ? (
              <div>
                <h4>You submitted successfully!</h4>
                <button className="btn btn-success" onClick={this.newSearch}>
                   New 
                </button>
                </div>
            ) : (
            <div>
            
              <form>
                <table>
                    <tr>
                        <td>Country: </td>
                        <td><input type="text" 
                                    className="form-control"
                                    value={this.state.country}
                                    onChange={this.onChangeCountry}
                                    name="country"
                                />
                        </td>
                    </tr>
                    <tr>
                        <td>Category: </td>
                        <td><input type="text" 
                                className="form-control"
                                value={this.state.category}
                                onChange={this.onChangeCategory}
                                name="category"
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>Keywords: </td>
                        <td><input type="text" 
                                className="form-control"
                                value={this.state.keywords}
                                onChange={this.onChangeKeywords}
                                name="keywords"
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <Router>
                                <Link to={"/ArticleList"}>
                                    <button onClick={this.saveSearch} className="btn btn-success">
                                        Search
                                    </button>
                                </Link>
                            </Router>
                        </td>
                    </tr>
                </table>
              </form>

                {/* <Switch>
                    <Route path='/:country/:category' component={ArticleList} />
                </Switch> */}

            </div>
            )}
        </div>
        );
    }
}