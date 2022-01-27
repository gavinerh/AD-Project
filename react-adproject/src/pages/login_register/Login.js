import { useEffect, useState } from 'react';
import AuthenticationService from '../../service/AuthenticationService';
import UserDataService from '../../service/UserDataService';
import {useHistory} from 'react-router-dom';

function Login() {
    const history = useHistory();
    if(AuthenticationService.isUserLoggedIn() === 'true') {
        history.push('/main');
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
                setErrorStatement('');
                history.push("/main");
            }).catch(response => {
                setErrorStatement('Wrong username or password');
            })
    }

    return (
        <div>
            <h1>Login</h1>
            <form onSubmit={loginFormHandler}>
                <label>Username: </label>
                <input type="text" name='description' onChange={usernameChangeHandler} />

                <br />
                <label>Password: </label>
                <input type="password" name='password' onChange={passwordChangeHandler} />
                {errorStatement !== '' && <p>{errorStatement}</p>}
                <button onClick={loginFormHandler}>Submit</button>
            </form>
        </div>
    )
}

export default Login;