import { useState } from 'react';
import UserDataService from '../../service/UserDataService';

function Login() {
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

    const loginFormHandler = () => {
        UserDataService.login(userCredential.username, userCredential.password)
        .then(
            // redirect to new page
        ).catch(
            // display error message like if username is used
            // remain at this page
        )
    }

    return (
        <div>
            <h1>Login</h1>
            <form onSubmit={loginFormHandler}>
                    <label>Username: </label>
                    <input type="text" name='description' onChange={usernameChangeHandler}/>
                
                    <br/>
                    <label>Password: </label>
                    <input type="password" name='password' onChange={passwordChangeHandler} />
                    <button onClick={loginFormHandler}>Submit</button>
            </form>

        </div>
    )
}

export default Login;