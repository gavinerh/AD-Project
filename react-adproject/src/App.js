import 'devextreme/dist/css/dx.common.css';
import 'devextreme/dist/css/dx.light.css';
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
import UpdateCategory from './components/UpdateCategory';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Switch>
          <Route exact path="/" component={Login} />
          <Route path="/login" component={Login} />
          <Route path="/register" component={Register} />
          <AuthenticatedRoute path="/main" component={MainPage} />
          <AuthenticatedRoute path="/main/settings" component={Settings} />
          <AuthenticatedRoute path="/main/updateuser" component={UserDetails} />
          <Route path="/main/updateCategory" component={UpdateCategory} />
          <Route path='/main/ArticleList' component={ArticleList} />

          <Route path="*" component={PageError} />
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
