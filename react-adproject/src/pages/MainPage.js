import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import ArticleList from '../components/ArticleList';
import SidebarComponent from "../components/SidebarComponent";
import './MainPage.css';
import Settings from './Settings';
import UserDetails from "../components/UserDetails";

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
  const routes = [
    {
      path: "/main",
      exact: true,
      main: () => <ArticleList />
    },
    {
      path: "/main/ArticleList",
      exact: true,
      main: () => <ArticleList />
    },
    {
      path: "/main/bookmark",
      exact: true,
      main: () => <h2>Bookmark</h2> //include bookmark component here
    },
    {
      path: "/main/settings",
      exact: true,
      main: () => <Settings />
    },
    {
      path: "/main/updateuser",
      exact: true,
      main: () => <UserDetails />
    }
  ];

  return (

    <div className='wrapper'>

      {/* display sidebar */}
      <div className='col-sidebar col-md-3 .d-none .d-md-block'>
        <SidebarComponent />
      </div>

      {/* main container to display the relevant page */}
      <div className='col-maincontainer col-md-9 justify-content-center mb-3'>
        <Switch>
          {routes.map((route, index) => (
            // Render <Route>s path to this component
            <Route
              key={index}
              path={route.path}
              exact={route.exact}
              children={<route.main />}
            />
          ))}
        </Switch>
      </div>

    </div>
  );
}

export default MainPage;

