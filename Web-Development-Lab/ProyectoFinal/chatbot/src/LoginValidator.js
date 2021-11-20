import React from 'react';
import App from "./App";
import { useAuth0 } from "@auth0/auth0-react";

const Profile = () => {
  const {isAuthenticated, isLoading, loginWithRedirect} = useAuth0();

  if (isLoading) {
    return <div>Loading ...</div>;
  }

  return isAuthenticated?
  (
    <App/>
  ) :
  (
    loginWithRedirect()
  )
  ;
};

class LoginValidator extends React.Component{
  constructor(props){
    super(props);
    this.state = {};
  }

    render(){
      return(
        <Profile />
      );
    }
}

export default LoginValidator ;