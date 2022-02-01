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
    <div className='row-mainPage'>

      <SidebarComponent />
      <ArticleList />

    </div>
  );
}

export default MainPage;

