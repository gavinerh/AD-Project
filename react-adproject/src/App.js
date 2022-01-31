import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Login from './pages/login_register/Login';
import Register from './pages/login_register/Register';
import MainPage from './pages/MainPage';
import PageError from './pages/PageError';
import AuthenticatedRoute from './components/AuthenticatedRoute';
import Settings from './pages/Settings';
import UserDetails from './components/UserDetails';
import ArticleList from './components/ArticleList'
import Search from './components/Search'

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Switch>
          <Route exact path="/" component={Login} />
          <Route path="/login" component={Login} />
          <Route path="/register" component={Register} />
          <AuthenticatedRoute path="/main" component={MainPage} />
          <AuthenticatedRoute path="/settings" component={Settings} />
          <AuthenticatedRoute path="/updateuser" component={UserDetails} />
          <Route path='/ArticleList' component={ArticleList} />
          <Route path='/Search' component={Search} />
          <Route path="*" component={PageError} />
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
