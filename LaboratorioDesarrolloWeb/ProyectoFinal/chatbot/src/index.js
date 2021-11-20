import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import LoginValidator from './LoginValidator';
import * as serviceWorker from './serviceWorker';
import {Auth0Provider} from '@auth0/auth0-react';

import './assets/css/bootstrap/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min';

ReactDOM.render(
  <Auth0Provider
    domain = "dev-x97wd7s0.auth0.com"
    clientId = "S3k4NIyWmQkxLKudNCYVoENrbwP8ElLg"
    redirectUri = {window.location.origin}
  >
    <LoginValidator />
  </Auth0Provider>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
