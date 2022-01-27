// will contain the news app
import {useHistory} from 'react-router-dom'

import AuthenticationService from "../service/AuthenticationService";

function MainPage(){
    const history = useHistory();
    const logoutHandler = () =>{
        AuthenticationService.removeUserSession();
        history.push("/");
    }
    return (
        <div>
            <h2>You have successfully logged in</h2>
            <button onClick={logoutHandler}>Logout</button>
        </div>
    )
}

export default MainPage;