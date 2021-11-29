import {Component} from 'react';
import {Link, Outlet, useNavigate} from 'react-router-dom';
import useAuth from './user/useAuth.js';

function NavigationBar () {
  const {authed, logout} = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/");
  }

  return(
      <div>
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
          <div className="container-fluid">
            <a className="navbar-brand">Navbar</a>
            <button className="navbar-toggler" type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarTogglerDemo01"
                    aria-controls="navbarTogglerDemo01" aria-expanded="false"
                    aria-label="Toggle navigation">
              <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarSupportedContent">
              <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                <li className="nav-item">
                  <Link className="nav-link" to="/">
                    Home
                  </Link>
                </li>
                { authed === true ? (<>
                    <li className="nav-item">
                      <Link className="nav-link" to="/personal_log">Personal Log</Link>
                    </li>
                    <li className="nav-item">
                      <Link className="nav-link" to="/messages">Read Messages</Link>
                    </li>
                    <li className="nav-item">
                      <Link className="nav-link" to="/send_message">Send Message</Link>
                    </li></>) : (
                    <>
                      <li className="nav-item">
                        <Link className="nav-link" to="/login">
                          Login
                        </Link>
                      </li>,
                      <li className="nav-item">
                        <Link className="nav-link" to="/register">
                          Register
                        </Link>
                      </li>
                    </>)
                }
                <li className="nav-item">
                  <Link className="nav-link" to="/search">Search</Link>
                </li>
              </ul>
              { authed ? (
                  <button className="me-2 btn btn-outline-success" onClick={handleLogout}>Logout</button>) : <></>}
            </div>
          </div>
        </nav>
        <Outlet />
      </div>
      )
}



export default NavigationBar;