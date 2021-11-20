import React from "react";
import {useAuth0} from '@auth0/auth0-react'
import {withRouter} from 'react-router-dom';
import { Link } from 'react-router-dom';


const NavBar = () => {

    const { user, logout } = useAuth0();

        return (
            <nav className="navbar navbar-light navbar-expand bg-white shadow mb-4 topbar static-top">
            <div className="container mr-5 p-3">
                <ul className="nav navbar-nav flex-nowrap ml-auto">
                    <li style={{marginRight: '450px', paddingTop: '20px'}}>
                    </li>
                    <li className="nav-item dropdown no-arrow">
                        <div className="nav-item dropdown no-arrow pt-2">
                            <a className="dropdown-toggle nav-link" data-toggle="dropdown" aria-expanded="false" href="/">
                                <span className="d-none d-lg-inline mr-2 text-gray-600 small">{user.given_name}</span>
                                <img className="border rounded-circle img-profile" src={user.picture} alt="img"/>
                            </a>
                            <div className="dropdown-menu shadow dropdown-menu-right animated--grow-in">
                                    <Link className="dropdown-item" to="/profile">Profile</Link>
                                    <div className="dropdown-divider"></div>
                                    <button className="dropdown-item" onClick={ () => logout( { returnTo: window.location.origin})}>Logout</button>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </nav>
        );
}

export default withRouter(NavBar);
