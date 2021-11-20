import React from "react";
import { Row, Col } from 'react-bootstrap';
import { TwitterTimelineEmbed } from 'react-twitter-embed';
import Layout from '../Components/Layout';
import {useAuth0} from '@auth0/auth0-react'


const Twitter = () => {
    const { user } = useAuth0();
    console.log(user.sub);
    var text = user.sub.split('|');
    console.log(text);
    if (text[0] === "twitter") {
        return (
            <Layout>
                <h1 style={{textAlign: 'center'}}>Twitter Profile</h1>
                <Row className="ml-3 mr-3">
                    <Col md={12} lg={12} style={{marginTop: 32}}>
                        <center>
                            <TwitterTimelineEmbed
                                sourceType="profile"
                                userId={parseInt(text[1])}
                                options={{height: 700, width: 1000}}
                                />
                        </center>
                    </Col>
                </Row>
                <h1 style={{textAlign: 'center'}}>Likes</h1>
                <Row className="ml-3 mr-3">
                    <Col md={12} lg={12} style={{marginTop: 32}}>
                        <center>
                            <TwitterTimelineEmbed
                            sourceType="likes"
                            userId={parseInt(text[1])}
                            options={{height: 700, width: 1000}}
                            />
                        </center>
                    </Col>
                </Row>
            </Layout>

        );
    }
    else {
        return (
            <Layout>
                <Row className="ml-3 mr-3">
                    <Col md={12} lg={12} style={{marginTop: 32}}>
                        <h3 align="center">Haz Login con una cuenta de Twitter para ver el Feed</h3>
                    </Col>
                </Row>
            </Layout>

        );
    }
    };

export default Twitter;