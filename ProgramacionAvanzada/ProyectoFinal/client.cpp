/************************************************************
Gabriel Schlam Huber
Simon Metta Grego
Isaac Harari Masri

Client Program
This program acts as the client displaying the game for the different
users using a GUI that has premade windows for every client necessity
************************************************************/

#include <SFML/Graphics.hpp>
#include <SFML/Network.hpp>
#include <iostream>
#include <string>
#include <cstring>

#include "gui_lib.h"

using namespace std;
using namespace sf;

#define BUFFER_SIZE 1024

string splitQuestion(string str, string playername)
{
	size_t pos = 0;
	string question;
	string A;
	string B;
	string C;
	string D;
	string delimiter = ":";

	GUI gui;
	string answer;

	pos = str.find(delimiter);
	question = str.substr(0, pos);
	str.erase(0, pos + delimiter.length());

	pos = str.find(delimiter);
	A = str.substr(0, pos);
	str.erase(0, pos + delimiter.length());

	pos = str.find(delimiter);
	B = str.substr(0, pos);
	str.erase(0, pos + delimiter.length());

	pos = str.find(delimiter);
	C = str.substr(0, pos);
	str.erase(0, pos + delimiter.length());

	pos = str.find(delimiter);
	D = str.substr(0, pos);
	D.resize(D.size() - 1);

	answer = gui.ask_question(question, A, B, C, D, playername);

	return answer;
}

int main()
{
	GUI gui;
	int score_value = 0;
	int past_value = 0;

	string playername;

	IpAddress address = gui.select_server_window();
	const int port = 5000;

	TcpSocket socket1;
	if (socket1.connect(address, port) == Socket::Done)
	{
		gui.successfull_connection();
	}
	else 
	{
		gui.failed_connection();
		return 0;
	}

	// For sending data
	char data[BUFFER_SIZE];

	// For checking number of iterations made, for validations
	int numberIterations = 0;

	// ID given by the server for future references
	string id;

	Packet packet;
	while (true)
	{
		// If first iteration, ask name to player and send it to server
		if (numberIterations == 0)
		{
			playername = gui.set_name_window();
			strcpy(data, playername.c_str());

			packet << data;

			socket1.send(packet);

			cout << "Waiting for other players to connect" << '\n';
			cout << "Thank your for your patience..." << '\n';

			packet.clear();
		}

		// For receiving data
		char buffer[BUFFER_SIZE];
		string currentBuffer;
		string currentData;

		size_t received = 0;
		if (socket1.receive(buffer, sizeof(buffer), received) == Socket::Done)
		{
			// If first iteration, receive ID from server
			if (numberIterations == 0)
			{
				size_t pos = 0;
				string delimiter = ":";

				currentBuffer = buffer;

				// Ignoring first part of string
				pos = currentBuffer.find(delimiter);
				currentData = currentBuffer.substr(0, pos);
				currentBuffer.erase(0, pos + delimiter.length());

				// Split data and save ID from server
				pos = currentBuffer.find(delimiter);
				id = currentBuffer.substr(0, pos);

				// Preparing and sending reply
				strcpy(data, "ACK");

				packet << data;

				socket1.send(packet);

				packet.clear();
			}

			else
			{
				size_t pos = 0;
				string delimiter = ":";

				currentBuffer = buffer;

				pos = currentBuffer.find(delimiter);
				currentData = currentBuffer.substr(0, pos);

				// If server is sending score, receive it and display it
				if (currentData == "SCORE")
				{
					currentBuffer.erase(0, pos + delimiter.length());

					string score;

					// Split data and save score
					pos = currentBuffer.find(delimiter);
					score = currentBuffer.substr(0, pos);

					score_value = stoi(score);

					if(score_value > past_value)
					{
						gui.correct_answer(score_value);
					}
					else
					{
						gui.incorrect_answer(score_value);
					}

					past_value = score_value;

					// Preparing and sending reply
					strcpy(data, "ACK");

					packet << data;

					socket1.send(packet);

					packet.clear();
				}

				// If server is sending bye, receive final score and winner
				else if (currentData == "BYE")
				{
					currentBuffer.erase(0, pos + delimiter.length());

					string score;
					string winner;
					string scoreWinner;

					// Receiving winner
					pos = currentBuffer.find(delimiter);
					winner = currentBuffer.substr(0, pos);
					currentBuffer.erase(0, pos + delimiter.length());

					// Receiving winner's score
					pos = currentBuffer.find(delimiter);
					scoreWinner = currentBuffer.substr(0, pos);
					currentBuffer.erase(0, pos + delimiter.length());

					// Receiving player's score
					pos = currentBuffer.find(delimiter);
					score = currentBuffer.substr(0, pos);

					gui.show_winner(winner, stoi(scoreWinner), playername, stoi(score));

					cout << "Game ended" << endl;
					return 0;
				}

				// We are in the middle of the game
				else
				{
					string answer;

					// Split question and display it to player
					string split = string(buffer, received);
					answer = splitQuestion(split, playername);

					strcpy(data, answer.c_str());

					// Preparing reply, with answer and ID for server's calculations
					sprintf(data, "%s:%s", data, id.c_str());

					packet << data;

					socket1.send(packet);

					packet.clear();
				}
			}
		}
		numberIterations++;
	}
}
