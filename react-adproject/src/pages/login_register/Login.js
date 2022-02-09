import { useEffect, useState } from 'react';
import axios from 'axios';
import AuthenticationService from '../../service/AuthenticationService';
import UserDataService from '../../service/UserDataService';
import { useHistory, Link, Redirect } from 'react-router-dom';
import './login_register.css';

function Login() {
    const history = useHistory();
    if (AuthenticationService.isUserLoggedIn() === 'true') {
        // history.push('/main');
        <Redirect push to="/main" />
    }
    const [errorStatement, setErrorStatement] = useState('');
    const [userCredential, setUserCredential] = useState({
        username: '',
        password: ''
    });

    const usernameChangeHandler = (event) => {
        setUserCredential({
            ...userCredential,
            username: event.target.value
        });
    }

    const passwordChangeHandler = (event) => {
        setUserCredential({
            ...userCredential,
            password: event.target.value
        });
    }

    const loginFormHandler = (event) => {
        event.preventDefault();
        UserDataService.login(userCredential.username, userCredential.password)
            .then(response => {
                console.log(response);
                AuthenticationService.storeUserSession(userCredential.username);
                AuthenticationService.registerSuccessfulLogin(response.data.jwt);
                setErrorStatement('');
                history.push("/main");
            }).catch(response => {
                setErrorStatement('Wrong email or password');
            })
    }

    return (
        <div className="modal modal-signin position-static d-block py-5">
            <div className="modal-dialog">
                <div className="modal-content rounded-5 shadow">
                    <div className="modal-header pt-5 px-5 border-bottom-0">
                        <h1 className="my-brand fw-normal">NEWSBOOK</h1>
                    </div>
                    <div className="modal-header px-5 border-bottom-0">
                        <h2 className="fs-4">Login</h2>
                    </div>

                    <div className="modal-body p-5 pt-0">
                        <form onSubmit={loginFormHandler}>
                            <div className="form-floating mb-3">
                                <input onChange={usernameChangeHandler} name="username" type="email" className="form-control rounded-4" placeholder="Email address" />
                                <label htmlFor="floatingInput">Email address</label>
                            </div>
                            <div className="form-floating mb-3">
                                <input onChange={passwordChangeHandler} name="password" type="password" className="form-control rounded-4" placeholder="Password" />
                                <label htmlFor="floatingPassword">Password</label>
                                {errorStatement !== '' && <span className="fw-bold text-danger">{errorStatement}</span>}
                            </div>
                            <button onClick={loginFormHandler} className="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit">Login</button>
                        </form>
                        <hr className="my-4"></hr>
                        <h2 className="fs-5 fw-bold mb-3">Create a new account</h2>
                        <Link className="w-100 py-2 mb-2 btn btn-outline-primary rounded-4" to="/register">Sign up for free</Link>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Login;