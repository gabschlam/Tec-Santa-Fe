import React from "react";
import image from '../Images/whatsappTwilio.png';
import { Row, Col } from 'react-bootstrap';
import Layout from '../Components/Layout';


class WhatsApp extends React.Component{
    render(){
        return (
            <Layout>
                <Row className="ml-3 mr-3">
                    <Col md={12} lg={12} style={{marginTop: 32}}>
                        <h1 align="center">WhatsApp</h1>
                        <h3 align="center">Escanea el siguiente código QR para contactarnos por WhatsApp</h3>
                        <center>
                            <img src={image} alt="whatsApp"/>
                        </center>
                    </Col>
                </Row>
            </Layout>

        );
    }
}

export default WhatsApp;