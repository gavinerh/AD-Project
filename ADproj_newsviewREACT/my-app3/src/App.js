import React from 'react';
import './App.css';
import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import ArticleList from './Component/ArticleList';
import Search from './Component/Search';

function App() {
  return (
    <div className="App">
      <Router>
        <div>
          <nav className="navbar navbar-expand navbar-dark bg-dark">
          <div className="navbar-nav mr-auto">
            <li>My News App</li>
            <li className="nav-item">
              <Link to={"/ArticleList"} className="nav-link">
                Home-Articles
              </Link>
            </li>
            <li className="nav-item">
              <Link to={"/Search"} className="nav-link">
                Search
              </Link>
            </li>
          </div>
        </nav>
        </div>
        <div className="container mt-3">
          <Switch>
            <Route exact path='/ArticleList' component={ArticleList} />
            <Route exact path='/Search' component={Search} />
          </Switch>
        </div>
      </Router>
      </div>
  );
}
export default App;
