// will contain the news app
import { Link } from 'react-router-dom'
import ArticleList from '../components/ArticleList';
import SidebarComponent from "../components/SidebarComponent";
import './MainPage.css';

import AuthenticationService from "../service/AuthenticationService";

function MainPage() {
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

    <div className='wrapper'>

      <div className='col-sidebar col-xs-3 col-sm-3 col-md-3 col-lg-2'>
        <SidebarComponent />
      </div>

      <div className='col-articlelist col-xs-9 col-sm-9 col-md-9 col-lg-10'>
        <ArticleList />
      </div>

    </div>
  );
}

export default MainPage;

