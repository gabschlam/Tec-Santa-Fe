import React from "react";
import { Row, Col } from 'react-bootstrap';
import Layout from '../Components/Layout';

const Instagram = () => {
    
    return (
        <Layout>
            <Row className="ml-3 mr-3">
                <Col md={12} lg={12} style={{marginTop: 32}}>
                    <h3 align="center">En este momento no se puede mostrar datos. Intenta más tarde</h3>
                </Col>
            </Row>
        </Layout>

    );
};

export default Instagram;