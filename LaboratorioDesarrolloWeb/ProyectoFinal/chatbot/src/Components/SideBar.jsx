import React from 'react';
import { Link } from 'react-router-dom';

import home from '../Images/home.png'
import facebook from '../Images/facebook.png'
import whatsapp from '../Images/whatsapp.png'
import instagram from '../Images/instagram.png'
import twitter from '../Images/twitter.png'

function Sidebar(props) {
    return (
        <div className="navbar navbar-dark align-items-start sidebar sidebar-dark accordion bg-gradient-primary p-0">
            <div className="container-fluid d-flex flex-column p-0">
                <a className="navbar-brand d-flex justify-content-center align-items-center sidebar-brand m-0" href="/">
                    <div className="sidebar-brand-text mx-3"><span>Gaby</span></div>
                </a>
                <hr className="sidebar-divider my-0"/>
                <ul className="nav navbar-nav text-light" id="accordionSidebar">
                    <li className="nav-item"><Link className="nav-link active" to="/"><img style={{height: 21, borderRadius: 100, marginRight:8}} src={home} alt="dashboard"/> Dashboard </Link></li>
                    <li className="nav-item"><Link className="nav-link" to="/whatsapp"><img style={{height: 24, borderRadius: 100, marginRight:8}} src={whatsapp} alt="whatsapp"/> WhatsApp </Link></li>
                    <li className="nav-item"><Link className="nav-link" to="/instagram"><img style={{height: 26, marginRight:8}} src={instagram} alt="instagram"/>  Instagram</Link></li>
                    <li className="nav-item"><Link className="nav-link" to="/twitter"><img style={{height: 34, paddingBottom: 5, marginRight:8}} src={twitter} alt="twitter"/>Twitter</Link></li>
                    <li className="nav-item"><Link className="nav-link" to="/instagram"><img style={{height: 24, marginRight:8}} src={facebook} alt="facebook"/>  Facebook</Link></li>

                </ul>
            </div>
        </div>
    );
}

export default Sidebar;