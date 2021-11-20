# API
import os
import json
import logging
import requests
import datetime
import locale
import time
from time import sleep

import flask
from flask import Flask, request
from flask_restful import Resource, Api
from flask_cors import CORS
from dotenv import load_dotenv
from flask_api import status

from jsonschema import validate, ValidationError
from ibm_watson import AssistantV2, ApiException
from ibm_cloud_sdk_core.authenticators import IAMAuthenticator
from flask import jsonify

from IBM_Whatson import *
from Mongo_Connection import *

from twilio.rest import Client


load_dotenv()

app = Flask(__name__)
api = Api(app)
cors = CORS(app, resources={r"/*": {"origins": "*"}})


class GET_MESSAGE(Resource):
    def post(self):
        message = request.json["message"]
        resp = watson_response(watson_create_session(), message)
        #print(resp)
        if resp["response"]["output"]["intents"] == []:
            return jsonify(
                text= resp["response"]["output"]["generic"][0]["text"],
                intent= resp["response"]["output"]["intents"]
            )
        else:
            intent = resp["response"]["output"]["intents"][0]["intent"]
            insertUserData(message, intent, "web")
            if intent == "Postear_Contenido":
                return jsonify(
                    text= resp["response"]["output"]["generic"][0]["text"],
                    intent= intent
                )
            else:
                return jsonify(
                    text= getWatsonResponseDB(intent),
                    intent= intent
                )

class GET_MESSAGE_WHATSAPP(Resource):
    def post(self):
        locale.setlocale(locale.LC_TIME, 'es_ES.UTF-8')

        print("message received: " + request.values.get('Body', '').lower())
        print("from: " + request.values.get('From', '').lower())

        account_sid = os.environ['TWILIO_ACCOUNT_SID']
        auth_token = os.environ['TWILIO_AUTH_TOKEN']
        client = Client(account_sid, auth_token)

        if request.values.get('Latitude', '').lower()!='' and request.values.get('Longitude', '').lower()!='':
            latitude = request.values.get('Latitude', '').lower()
            longitude = request.values.get('Longitude', '').lower()
            print("Latitude: " + latitude)
            print("Longitude: " + longitude)
            text = latitude + "," + longitude
            response = requests.get(
                url="https://api.predicthq.com/v1/events",
                headers={
                "Authorization": "Bearer " + os.getenv("predicthq_key"),
                "Accept": "application/json"
                },
                params={
                "location_around.origin": text,
                "limit": 4
                }
            )

            res = response.json()

            message = client.messages.create(
                from_='whatsapp:+14155238886',
                body="A continuación tus eventos cercanos:",
                to=request.values.get('From', '').lower()
            )

            for event in res["results"]:
                dateStart = datetime.datetime.strptime(event["start"], '%Y-%m-%dT%H:%M:%SZ')
                dateS = dateStart.strftime("%B %d, %Y, %H:%M:%S")
                dateEnd = datetime.datetime.strptime(event["end"], '%Y-%m-%dT%H:%M:%SZ')
                dateE = dateEnd.strftime("%B %d, %Y, %H:%M:%S")
                
                if event["entities"]:
                    bod = "Evento: " + event["title"] + '\n'  + "Categoría: " + event["category"] +  '\n'  + "Fecha y hora de inicio: " + str(dateS) +  '\n'  +  "Fecha y hora de fin: " + str(dateE) + '\n'  + "Dirección: " + event["entities"][0]["formatted_address"],
                    pass
                if not event["entities"]:
                    bod = "Evento: " + event["title"] + '\n'  + "Categoría: " + event["category"] +  '\n'  + "Fecha y hora de inicio: " + str(dateS) +  '\n'  +  "Fecha y hora de fin: " + str(dateE) + '\n',
                    pass
                                    
                message = client.messages.create(
                    from_='whatsapp:+14155238886',
                    body= bod,
                    to=request.values.get('From', '').lower()
                )
                message = client.messages.create(
                    from_='whatsapp:+14155238886',
                    body= "Evento: " + event["title"] + '\n',
                    persistent_action= ["geo:" + str(event["location"][1]) + "," + str(event["location"][0])],
                    to=request.values.get('From', '').lower()
                )

                time.sleep(1)
                
                pass
            
            
            return

        message = request.values.get('Body', '').lower()
        resp = watson_response(watson_create_session(), message)
        #print(resp)
        if resp["response"]["output"]["intents"] == []:
            message = client.messages.create(
                from_='whatsapp:+14155238886',
                body=resp["response"]["output"]["generic"][0]["text"],
                to=request.values.get('From', '').lower()
            )
        else:
            intent = resp["response"]["output"]["intents"][0]["intent"]
            insertUserData(message, intent, "whatsApp")

            if intent == "Postear_Contenido":
                message = client.messages.create(
                    from_='whatsapp:+14155238886',
                    body=resp["response"]["output"]["generic"][0]["text"],
                    to=request.values.get('From', '').lower()
                )
            elif intent == "Mas_Populares":
                message = client.messages.create(
                    from_='whatsapp:+14155238886',
                    body="A continuación tus imágenes más populares:",
                    to=request.values.get('From', '').lower()
                )
                for img in getWatsonResponseDBWhatsApp(intent):
                    message = client.messages.create(
                        from_='whatsapp:+14155238886',
                        media_url=img,
                        to=request.values.get('From', '').lower()
                    )
            else:
                message = client.messages.create(
                    from_='whatsapp:+14155238886',
                    body=getWatsonResponseDBWhatsApp(intent),
                    to=request.values.get('From', '').lower()
                )

class GET_EVENTS(Resource):
    def post(self):
        print(request.json)
        latitude = request.json["latitude"]
        longitude = request.json["longitude"]
        
        print(latitude)
        print(longitude)

        text = str(latitude) + "," + str(longitude)

        response = requests.get(
            url="https://api.predicthq.com/v1/events",
            headers={
            "Authorization": "Bearer " + os.getenv("predicthq_key"),
            "Accept": "application/json"
            },
            params={
            "location_around.origin": text,
            "limit": 4
            }
        )

        res = response.json()
        print(res)

        resultFormatted = []

        for event in res["results"]:
            arr = []
            dateStart = datetime.datetime.strptime(event["start"], '%Y-%m-%dT%H:%M:%SZ')
            dateS = dateStart.strftime("%B %d, %Y, %H:%M:%S")
            dateEnd = datetime.datetime.strptime(event["end"], '%Y-%m-%dT%H:%M:%SZ')
            dateE = dateEnd.strftime("%B %d, %Y, %H:%M:%S")
                
            if event["entities"]:
                bod = "Evento: " + event["title"] + '  \n'  + "Categoría: " + event["category"] +  '  \n'  + "Fecha y hora de inicio: " + str(dateS) +  '  \n'  +  "Fecha y hora de fin: " + str(dateE) + '  \n'  + "Dirección: " + event["entities"][0]["formatted_address"],
                pass
            if not event["entities"]:
                bod = "Evento: " + event["title"] + '  \n'  + "Categoría: " + event["category"] +  '  \n'  + "Fecha y hora de inicio: " + str(dateS) +  '  \n'  +  "Fecha y hora de fin: " + str(dateE) + '  \n',
                pass
            
            arr.append(bod)
            arr.append(event["location"])

            resultFormatted.append(arr)

        return resultFormatted

api.add_resource(GET_MESSAGE, '/getMessage')  # Route_1
api.add_resource(GET_MESSAGE_WHATSAPP, '/getMessageWhatsapp')  # Route_2
api.add_resource(GET_EVENTS, '/getEvents')  # Route_3

if __name__ == '__main__':
    app.run(port='5002')
