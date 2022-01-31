// will contain the news app
import {Link} from 'react-router-dom'
import ArticleList from '../components/ArticleList';

import AuthenticationService from "../service/AuthenticationService";

function MainPage(){
    // const history = useHistory();
    // const logoutHandler = () =>{
    //     AuthenticationService.removeUserSession();
    //     history.push("/");
    // }
    // const settingsHandler = () => {
    //     history.push("/settings");
    // }
    // return (
    //     <div>
    //         <h2>You have successfully logged in</h2>
    //         <button onClick={logoutHandler}>Logout</button>
    //         <button onClick={settingsHandler}>Settings</button>
    //     </div>
    // )
    return (
        <div className="App">
            <div>
              <nav className="navbar navbar-expand navbar-dark bg-dark">
              <div className="navbar-nav mr-auto">
                <li>My News App</li>
                <li className="nav-item">
                  <Link to={"/ArticleList"} className="nav-link">
                    Home-Articles
                  </Link>
                </li>
                <li className="nav-item">
                  <Link to={"/Search"} className="nav-link">
                    Search
                  </Link>
                </li>
              </div>
            </nav>
            </div>
            <div className="container mt-3">
              <ArticleList />
            </div>
          </div>
      );
}

export default MainPage;

