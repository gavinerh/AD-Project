// will contain the news app
import {useHistory} from 'react-router-dom'

import AuthenticationService from "../service/AuthenticationService";

function MainPage(){
    const history = useHistory();
    const logoutHandler = () =>{
        AuthenticationService.removeUserSession();
        history.push("/");
    }
    const settingsHandler = () => {
        history.push("/settings");
    }
    return (
        <div>
            <h2>You have successfully logged in</h2>
            <button onClick={logoutHandler}>Logout</button>
            <button onClick={settingsHandler}>Settings</button>
        </div>
    )
}

export default MainPage;