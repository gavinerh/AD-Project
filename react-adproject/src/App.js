import logo from './logo.svg';
import './App.css';
import React, { useState } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Login from './pages/login_register/Login';
import Register from './pages/login_register/Register';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Switch>
        <Route path="/" component={Login}/>
        <Route path="/register" component={Register}/>


        {/* Error component must be last for catch all routes */}
        <Route path="*" component={Error} />
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
