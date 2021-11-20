import React from "react";
import Home from "./Pages/Home";
import Profile from "./Pages/Profile";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Chatbot from "./Components/Chatbot";
import "react-chat-widget/lib/styles.css";
import WhatsApp from "./Pages/WhatsApp";
import FormPage from "./Pages/Login";
import Twitter from "./Pages/Twitter";
import Instagram from "./Pages/Instagram";
import Facebook from "./Pages/Facebook";

export default function App() {
  return (
    <Router>
      <Switch>
        <Route exact path="/">
          <Home />
        </Route>
        <Route path="/home">
          <Home />
        </Route>
        <Route path="/profile">
          <Profile />
        </Route>
        <Route path="/whatsApp">
          <WhatsApp />
        </Route>
        <Route path="/twitter">
          <Twitter />
        </Route>
        <Route path="/instagram">
          <Instagram />
        </Route>
        <Route path="/facebook">
          <Facebook />
        </Route>
        <Route path="/login">
          <FormPage/>
        </Route>
      </Switch>
      <Chatbot />
    </Router>
  );
}
