import logo from './logo.svg';
import './App.css';
import React, { useState } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Login from './pages/login_register/Login';
import Register from './pages/login_register/Register';
import MainPage from './pages/MainPage';
import PageError from './pages/PageError';
import AuthenticatedRoute from './components/AuthenticatedRoute';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Switch>
          <Route exact path="/" component={Login} />
          <AuthenticatedRoute path="/register" component={Register} />
          <AuthenticatedRoute path="/main" component={MainPage} />
          <Route path="*" component={PageError} />
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
