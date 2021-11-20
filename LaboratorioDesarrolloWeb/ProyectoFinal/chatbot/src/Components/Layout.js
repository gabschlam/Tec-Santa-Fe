import React from 'react';
import SideBar from './SideBar';
import NavBar from './NavBar';
const Layout = (props) => {
    return (
        <>
        <div id="wrapper">
            <SideBar/>
            <div className="d-flex flex-column" id="content-wrapper">
                <div id="content">
                    <div className="container-fluid p-0">
                        <NavBar />
                        {props.children}
                    </div>

                </div>
            </div>
        </div>
        <footer className="bg-white sticky-footer">
            <div className="container my-auto">
                <div className="text-center my-auto copyright"><span>Copyright © Gaby 2020</span></div>
            </div>
        </footer>
        </>  
    );
};

export default Layout;