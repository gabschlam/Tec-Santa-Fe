import React from "react";
import {Widget, renderCustomComponent, addResponseMessage} from "react-chat-widget";
import "react-chat-widget/lib/styles.css";
import ReactHtmlParser from "react-html-parser";
import axios from "axios";
//import { Carousel } from 'react-responsive-carousel';

class HtmlComponent extends React.Component {
  render() {
    return <div>{ReactHtmlParser(this.props.text)}</div>;
  }
}

/* class DemoCarousel extends React.Component{
      render(){
        return (
          <div>{ReactHtmlParser(
            <Carousel>
            {this.props.urls.forEach(url => (                  
              <div>
                <img src={url} alt="im" />
                  <p className="legend">Legend 1</p>
              </div>
            ))}
          </Carousel>
          )}</div>
        );
      }
} */

class Chatbot extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      latitude: null,
      longitude: null,
      text: null
    };
    this.getLocation = this.getLocation.bind(this);
    this.getCoordinates = this.getCoordinates.bind(this);
  }

  componentDidMount() {
    this.onInit();
  }

  onInit = () => {
    let message = "";
    axios.post("http://127.0.0.1:5002/getMessage", { message }).then((res) => {
      addResponseMessage(res.data.text);
    });
  };

  getEvents = async () => {
    addResponseMessage(this.state.text);
    let latitude = this.state.latitude;
    let longitude = this.state.longitude;
    let mapTemplate = '<iframe src="https://maps.google.com/maps?q=latitude, longitude&z=15&output=embed" width="360" height="270" frameborder="0" style="border:0"></iframe>'
    axios.post("http://127.0.0.1:5002/getEvents", { latitude: latitude, longitude:longitude }).then((res) => {
      console.log(res.data);
      let events = res.data;
      
      events.forEach(element => {
          console.log(element[0]);
          addResponseMessage(element[0][0]);
          let currentMap = mapTemplate.replace("latitude", String(element[1][1]));
          currentMap = currentMap.replace("longitude", String(element[1][0]));
          renderCustomComponent(HtmlComponent, { text: currentMap });

      });
    });
  }

  getLocation = async () => {
    navigator.permissions.query({name:'geolocation'}).then(function(result) {
      console.log(result);
      if (result.state !== 'granted') {
        addResponseMessage("Por favor autoriza tu ubicación en tu navegador para continuar");
      }
    });
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(this.getCoordinates, this.handleLocationError);
    } else {
      alert("Geolocation is not supported by this browser.");
    }
  }

  getCoordinates = async (position) => {
    console.log(position.coords.latitude);
    console.log(position.coords.longitude);
    this.setState({
      latitude: position.coords.latitude,
      longitude: position.coords.longitude
    })
    this.getEvents();

  }

  handleLocationError = (error) => {
    switch(error.code) {
      case error.PERMISSION_DENIED:
        alert( "User denied the request for Geolocation.")
        break;
      case error.POSITION_UNAVAILABLE:
        alert( "Location information is unavailable.")
        break;
      case error.TIMEOUT:
        alert( "The request to get user location timed out.")
        break;
      case error.UNKNOWN_ERROR:
        alert( "An unknown error occurred.")
        break;
      default:
        alert( "An unknown error occurred.")
        break;
    }
  }

  handleNewUserMessage = (message) => {
    axios.post("http://127.0.0.1:5002/getMessage", { message }).then((res) => {
      console.log(res.data);
      if(res.data.intent === "Eventos_Intent") {
        this.setState({
          text: res.data.text
        })
        this.getLocation();
      
      }
      else{ 
        renderCustomComponent(HtmlComponent, { text: res.data.text });
      }
      
    });
  };

  render() {
    return (
      <Widget
        handleNewUserMessage={this.handleNewUserMessage}
        title="Gaby"
        subtitle="Al servicio de la comunidad"
      />
    );
  }
}

export default Chatbot;
