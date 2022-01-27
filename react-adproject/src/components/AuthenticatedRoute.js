import {Route} from 'react-router-dom'
import AuthenticationService from '../service/AuthenticationService';
import Login from '../pages/login_register/Login';

function AuthenticatedRoute(props){
    if(AuthenticationService.isUserLoggedIn() === 'true'){
        return <Route path={props.path} component={props.component} />
    }else{
        return <Route path="/" component={Login} />
    }
}

export default AuthenticatedRoute;