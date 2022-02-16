import { BrowserRouter as Router, Switch, Route, Link, Redirect } from "react-router-dom";
import ArticleList from '../components/ArticleList';
import './MainPage.css';
import Settings from './Settings';
import UserDetails from "../components/UserDetails";
import WeatherComponent from '../components/WeatherComponent';
import AuthenticationService from "../service/AuthenticationService";
import Bookmark from "./Bookmark";

function MainPage() {

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
      main: () => <Bookmark /> //include bookmark component here
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

  const username = AuthenticationService.getUserEmail();

  const logoutHandler = () => {
    AuthenticationService.removeJwtToken();
    AuthenticationService.removeUserSession();
    <Redirect to="/login" />
  }

  return (
    <div>
      {/* toggle topbar */}
      <div className="d-sm-block d-md-none navbar navbar-expand-sm navbar-light sticky-top bg-light" aria-label="navbar">
        <div className="container-fluid navbar-brand">
          <span className="my-brand-sidebar fw-normal">NEWSBOOK</span>
          <button className="navbar-toggler d-sm-block d-md-none " type="button" data-bs-toggle="collapse"
            data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false"
            aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span></button>
        </div>

      </div>
      {/* sidebar */}
      <div className="container-fluid">
        <div className="row">
          <nav id="sidebarMenu" className="col-md-3 col-lg-2 d-md-block bg-light position-sticky sidebar collapse">
            <div className="pt-3">
              <ul className="nav flex-column">
                <li className="nav-item">
                  <span className="nav-link link-dark">
                    {/* weather info */}
                    <WeatherComponent />
                  </span>

                  <hr />
                </li>
                <li className="nav-item">
                  <span className="nav-link link-dark" aria-current="page">
                    <svg className="bi me-2" width="16" height="16">
                      <use xlinkHref="#home" />
                    </svg>
                    <Link to="/main" className="text-decoration-none text-dark">Home</Link>
                  </span>
                </li>
                <li className="nav-item">
                  <span className="nav-link link-dark">
                    <svg className="bi me-2" width="16" height="16">
                      <use xlinkHref="#bookmark-heart" />
                    </svg>
                    <Link to="/main/bookmark" className="text-decoration-none text-dark">Bookmark</Link>
                  </span>
                </li>
                <li className="nav-item">
                  <span className="nav-link link-dark">
                    <svg className="bi me-2" width="16" height="16">
                      <use xlinkHref="#hand-thumbs-up" />
                    </svg>
                    <Link to="/main/bookmark" className="text-decoration-none text-dark">Like</Link>
                  </span>
                </li>
                <li className="nav-item">
                  <span className="nav-link link-dark">
                    <svg className="bi me-2" width="16" height="16">
                      <use xlinkHref="#hand-thumbs-down" />
                    </svg>
                    <Link to="/main/bookmark" className="text-decoration-none text-dark">Dislike</Link>
                  </span>
                </li>
                <hr />
                <li className="nav-item">
                  <div class="collapsed" role="button" data-bs-toggle="collapse" data-bs-target="#home-collapse" aria-expanded="false">
                    <span className="nav-link text-break">
                      <svg className="bi me-2" width="16" height="16">
                        <use xlinkHref="#people-circle" />
                      </svg>
                      {username}&nbsp;
                      <svg className="bi me-2" width="16" height="16">
                        <use xlinkHref="#caret-down-fill" />
                      </svg>
                    </span>
                  </div>
                  <div class="collapse" id="home-collapse">
                    <ul class="btn-toggle-nav list-unstyled fw-normal pb-1">
                      <li><Link className='link-dark' to="/main/settings">Preferences</Link></li>
                      <li><Link className='link-dark' to="/main/updateuser">My account</Link></li>
                      <li><Link className='link-dark' to="/login" onClick={logoutHandler}>Sign out</Link></li>
                    </ul>
                  </div>
                </li>
              </ul>



            </div>
          </nav>
          {/* main container */}
          <div className="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <div>
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
          {/* end of main container */}
        </div>
      </div>

      {/* SIDEBAR ICONS */}
      <svg xmlns="http://www.w3.org/2000/svg" style={{ display: 'none' }}>
        <symbol id="home" viewBox="0 0 16 16">
          <path
            d="M8.354 1.146a.5.5 0 0 0-.708 0l-6 6A.5.5 0 0 0 1.5 7.5v7a.5.5 0 0 0 .5.5h4.5a.5.5 0 0 0 .5-.5v-4h2v4a.5.5 0 0 0 .5.5H14a.5.5 0 0 0 .5-.5v-7a.5.5 0 0 0-.146-.354L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.354 1.146zM2.5 14V7.707l5.5-5.5 5.5 5.5V14H10v-4a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5v4H2.5z" />
        </symbol>
        <symbol id="hand-thumbs-up" viewBox="0 0 16 16">
          <path fillRule="evenodd"
            d="M8.864.046C7.908-.193 7.02.53 6.956 1.466c-.072 1.051-.23 2.016-.428 2.59-.125.36-.479 1.013-1.04 1.639-.557.623-1.282 1.178-2.131 1.41C2.685 7.288 2 7.87 2 8.72v4.001c0 .845.682 1.464 1.448 1.545 1.07.114 1.564.415 2.068.723l.048.03c.272.165.578.348.97.484.397.136.861.217 1.466.217h3.5c.937 0 1.599-.477 1.934-1.064a1.86 1.86 0 0 0 .254-.912c0-.152-.023-.312-.077-.464.201-.263.38-.578.488-.901.11-.33.172-.762.004-1.149.069-.13.12-.269.159-.403.077-.27.113-.568.113-.857 0-.288-.036-.585-.113-.856a2.144 2.144 0 0 0-.138-.362 1.9 1.9 0 0 0 .234-1.734c-.206-.592-.682-1.1-1.2-1.272-.847-.282-1.803-.276-2.516-.211a9.84 9.84 0 0 0-.443.05 9.365 9.365 0 0 0-.062-4.509A1.38 1.38 0 0 0 9.125.111L8.864.046zM11.5 14.721H8c-.51 0-.863-.069-1.14-.164-.281-.097-.506-.228-.776-.393l-.04-.024c-.555-.339-1.198-.731-2.49-.868-.333-.036-.554-.29-.554-.55V8.72c0-.254.226-.543.62-.65 1.095-.3 1.977-.996 2.614-1.708.635-.71 1.064-1.475 1.238-1.978.243-.7.407-1.768.482-2.85.025-.362.36-.594.667-.518l.262.066c.16.04.258.143.288.255a8.34 8.34 0 0 1-.145 4.725.5.5 0 0 0 .595.644l.003-.001.014-.003.058-.014a8.908 8.908 0 0 1 1.036-.157c.663-.06 1.457-.054 2.11.164.175.058.45.3.57.65.107.308.087.67-.266 1.022l-.353.353.353.354c.043.043.105.141.154.315.048.167.075.37.075.581 0 .212-.027.414-.075.582-.05.174-.111.272-.154.315l-.353.353.353.354c.047.047.109.177.005.488a2.224 2.224 0 0 1-.505.805l-.353.353.353.354c.006.005.041.05.041.17a.866.866 0 0 1-.121.416c-.165.288-.503.56-1.066.56z" />
        </symbol>

        <symbol id="hand-thumbs-down" viewBox="0 0 16 16">
          <path fillRule="evenodd"
            d="M8.864 15.674c-.956.24-1.843-.484-1.908-1.42-.072-1.05-.23-2.015-.428-2.59-.125-.36-.479-1.012-1.04-1.638-.557-.624-1.282-1.179-2.131-1.41C2.685 8.432 2 7.85 2 7V3c0-.845.682-1.464 1.448-1.546 1.07-.113 1.564-.415 2.068-.723l.048-.029c.272-.166.578-.349.97-.484C6.931.08 7.395 0 8 0h3.5c.937 0 1.599.478 1.934 1.064.164.287.254.607.254.913 0 .152-.023.312-.077.464.201.262.38.577.488.9.11.33.172.762.004 1.15.069.13.12.268.159.403.077.27.113.567.113.856 0 .289-.036.586-.113.856-.035.12-.08.244-.138.363.394.571.418 1.2.234 1.733-.206.592-.682 1.1-1.2 1.272-.847.283-1.803.276-2.516.211a9.877 9.877 0 0 1-.443-.05 9.364 9.364 0 0 1-.062 4.51c-.138.508-.55.848-1.012.964l-.261.065zM11.5 1H8c-.51 0-.863.068-1.14.163-.281.097-.506.229-.776.393l-.04.025c-.555.338-1.198.73-2.49.868-.333.035-.554.29-.554.55V7c0 .255.226.543.62.65 1.095.3 1.977.997 2.614 1.709.635.71 1.064 1.475 1.238 1.977.243.7.407 1.768.482 2.85.025.362.36.595.667.518l.262-.065c.16-.04.258-.144.288-.255a8.34 8.34 0 0 0-.145-4.726.5.5 0 0 1 .595-.643h.003l.014.004.058.013a8.912 8.912 0 0 0 1.036.157c.663.06 1.457.054 2.11-.163.175-.059.45-.301.57-.651.107-.308.087-.67-.266-1.021L12.793 7l.353-.354c.043-.042.105-.14.154-.315.048-.167.075-.37.075-.581 0-.211-.027-.414-.075-.581-.05-.174-.111-.273-.154-.315l-.353-.354.353-.354c.047-.047.109-.176.005-.488a2.224 2.224 0 0 0-.505-.804l-.353-.354.353-.354c.006-.005.041-.05.041-.17a.866.866 0 0 0-.121-.415C12.4 1.272 12.063 1 11.5 1z" />
        </symbol>

        <symbol id="bookmark-heart" viewBox="0 0 16 16">
          <path fillRule="evenodd" d="M8 4.41c1.387-1.425 4.854 1.07 0 4.277C3.146 5.48 6.613 2.986 8 4.412z" />
          <path
            d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z" />
        </symbol>

        <symbol id="people-circle" viewBox="0 0 16 16">
          <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z" />
          <path fillRule="evenodd"
            d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z" />
        </symbol>

        <symbol id="caret-down-fill" viewBox="0 0 16 16">
          <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z" />
        </symbol>
      </svg>
    </div>
  );
}

export default MainPage;

