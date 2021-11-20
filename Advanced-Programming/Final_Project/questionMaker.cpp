/************************************************************
Gabriel Schlam Huber
Simon Metta Grego
Isaac Harari Masri

Server Program
This program is the one that sends the questions to all the clients
and calculates the amount of points they get based on their answers
************************************************************/

#include <SFML/Network.hpp>
#include <iostream>
#include <fstream>
#include <string>
#include <map>
#include <cstring>

// Libraries for random usage
#include <algorithm>    // random_shuffle
#include <vector>       // vector
#include <ctime>        // time
#include <cstdlib>      // rand, srand

using namespace std;
using namespace sf;

#define BUFFER_SIZE 1024
#define NUM_QUESTIONS 100
#define NUM_QUESTIONS_SELECTED 3
#define MAX_PLAYERS 2
#define RAND_QUESTIONS 5
#define MIN_SCORE 10

// Map for players. With struct: id, name [0[ and score [1]
map <int, std::vector<string> > players;

// Global variable for ignorance
int numCorrect = 0;


class Question
{
public:
	string question;
	string correctAnswer;
	string incorrectAnswer1;
	string incorrectAnswer2;
	string incorrectAnswer3;

	Question()
	{
		question = "";
		correctAnswer = "";
		incorrectAnswer1 = "";
		incorrectAnswer2 = "";
		incorrectAnswer3 = "";
	}

	Question(string q, string cA, string iA1, string iA2, string iA3) 
	{
		question = q;
		correctAnswer = cA;
		incorrectAnswer1 = iA1;
		incorrectAnswer2 = iA2;
		incorrectAnswer3 = iA3;
	}

	void printQuestion() 
	{
		cout << "Question: " << question << "\n";
		cout << "Correct Answer: " << correctAnswer <<"\n";
		cout << "Incorrect Answer 1: " << incorrectAnswer1 <<"\n";
		cout << "Incorrect Answer 2: " << incorrectAnswer2 <<"\n";
		cout << "Incorrect Answer 3: " << incorrectAnswer3 <<"\n";
		cout << '\n';
	}

	// Random generator function for randomizing order of answers before sending question
	static int randomFunct (int i) 
	{
		return rand()%i;
	}

	// Randomizing order of answers before sending question
	string getQuestionRand() 
	{
		srand(unsigned(time(0)));
		vector<int> myvector;
		string outstring;

		// Getting numbers from 1 to 4
		for (int i=1; i<5; ++i)
		{
			myvector.push_back(i);
		}

		// Using randomFunct for randomize order of vector (numbers 1 to 4)
		random_shuffle ( myvector.begin(), myvector.end(), randomFunct);

		// Preparing outstring with question and 4 possible answers
		outstring+=question;
		outstring+=":";

		cout << "Sending question: " << question << '\n';
		cout << "Correct answer: " << correctAnswer << '\n';

		for (vector<int>::iterator it=myvector.begin(); it!=myvector.end(); ++it)
		{
			switch(*it)
			{
				case 1:
					outstring+=correctAnswer;
					outstring+=":";
					break;
				case 2:
					outstring+=incorrectAnswer1;
					outstring+=":";
					break;
				case 3:
					outstring+=incorrectAnswer2;
					outstring+=":";
					break;
				case 4:
					outstring+=incorrectAnswer3;
					outstring+=":";
					break;

				default:
					break;
			}
		}

		// Delete last ":"
		outstring = outstring.substr(0, outstring.size()-1);

		return outstring;
	}
};

Question addQuestion(string str)
{
	size_t pos = 0;
	string question;
	string correctAnswer;
	string incorrectAnswer1;
	string incorrectAnswer2;
	string incorrectAnswer3;
	string delimiter = ":";

	pos = str.find(delimiter);
	question = str.substr(0, pos);
	str.erase(0, pos + delimiter.length());

	pos = str.find(delimiter);
	correctAnswer = str.substr(0, pos);
	str.erase(0, pos + delimiter.length());

	pos = str.find(delimiter);
	incorrectAnswer1 = str.substr(0, pos);
	str.erase(0, pos + delimiter.length());

	pos = str.find(delimiter);
	incorrectAnswer2 = str.substr(0, pos);
	str.erase(0, pos + delimiter.length());

	pos = str.find(delimiter);
	incorrectAnswer3 = str.substr(0, pos);

	Question q = Question(question, correctAnswer, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3);
	return q;
}


// Function for getting score after each answer
void getScore(string answer, string id, int numAct, std::string outstring, Question question)
{
	// Spliting outstring back to original form
	size_t pos = 0;

	string q;
	string answerResp;
	string A;
	string B;
	string C;
	string D;
	string delimiter = ":";

	int score;
	int idInt = std::atoi(id.c_str());

	pos = outstring.find(delimiter);
	q = outstring.substr(0, pos);
	outstring.erase(0, pos + delimiter.length());

	pos = outstring.find(delimiter);
	A = outstring.substr(0, pos);
	outstring.erase(0, pos + delimiter.length());

	pos = outstring.find(delimiter);
	B = outstring.substr(0, pos);
	outstring.erase(0, pos + delimiter.length());

	pos = outstring.find(delimiter);
	C = outstring.substr(0, pos);
	outstring.erase(0, pos + delimiter.length());

	pos = outstring.find(delimiter);
	D = outstring.substr(0, pos);


	// Checking wich answer corresponds to the player's answer
	if (answer == "A" or answer == "a")
	{
		answerResp = A;
	}

	else if (answer == "B" or answer == "b")
	{
		answerResp = B;
	}

	else if (answer == "C" or answer == "c")
	{
		answerResp = C;
	}

	else if (answer == "D" or answer == "d")
	{
		answerResp = D;
	}

	else
	{
		answerResp = "E";
	}


	// Check if answer is correct
	if (answerResp == question.correctAnswer)
	{
		// Score: depending on order of which player responded first, are the points giving
		score = stoi(players[idInt][1]) + MIN_SCORE * (MAX_PLAYERS + 1 - numAct);

		// Adding to the count of number of correct answers of the iteration.
		// If this number is cero at the end of the iteration, the ignorance gets the maximum score possible.
		numCorrect++;
	}
	else
	{
		// Leave the score as before
		score = stoi(players[idInt][1]);
	}

	// Assign the score back to the map
	players[idInt][1] = to_string(score);

}

// Determine winner, after the whole iterations made
string getWinner()
{
	string winner;

	// Map iterator
	map<int, std::vector<string> >::iterator it;

	int currentMax = 0;
	unsigned id = 0;

	for (it = players.begin(); it != players.end(); it++)
	{
		// If the score value is higher than the currentMax value, replace it
		if (stoi(it ->second[1]) > currentMax)
		{
			id = it->first;
			currentMax = stoi(it->second[1]);
		}
	}

	// Prepare the first part of the final string with the final scores: Winner's and player's
	winner = "BYE:" + players[id][0] + ":" + players[id][1];

	return winner;
}

// Entire loop for sending the questions to the client
void sendQuestions(Question questionsSelected[NUM_QUESTIONS_SELECTED])
{
	string outstring;

	vector<TcpSocket*> clients;

	TcpListener listener;
	listener.setBlocking(false);

	SocketSelector selector;

	const int port = 5000;
	int numPlayers = 0;
	// int id[MAX_PLAYERS];
	int index = 0;
	int numberIterations = 0;
	int numAct = 0;

	listener.listen(port);
	selector.add(listener);

	/*
		While: NUM_QUESTIONS_SELECTED * 2 because for every question sent, will be an acnkoledge.
		The +1 is because the first iteration where the player is asked for his name and his ID is sent.
	*/
	while(numberIterations < NUM_QUESTIONS_SELECTED * 2 + 1) 
	{
		if (selector.wait()) 
		{
			if (selector.isReady(listener)) 
			{
				// Handle the listener update
				TcpSocket* incomingClient = new TcpSocket;
				if (listener.accept(*incomingClient) == Socket::Done) 
				{
					// Handle socket selector for the incoming client
					clients.push_back(incomingClient);
					selector.add(*incomingClient);
					numPlayers++;
					cout << "Un cliente se ha conectado. IP: " << incomingClient->getRemoteAddress() << endl;

					// Refuse every connection after the number of maximum players connected is reached
					if (numPlayers == MAX_PLAYERS)
					{
						 cout << numPlayers << " players connected, not more allowed." << endl;
						 selector.remove(listener);
						 listener.close();
					}

				} 
				else
				{
					delete incomingClient;
				}
			}
			else
			{
				// Handle the socket update
				char data[BUFFER_SIZE];
				Packet packet;

				for (vector<TcpSocket*>::iterator it = clients.begin(); it != clients.end(); ++it)
				{
					TcpSocket& socket = **it;
					if (selector.isReady(socket))
					{
						if (socket.receive(packet) == Socket::Done)
						{

							char name[BUFFER_SIZE];
							string currentBuffer;
							string currentData;

							string answer;
							string response;
							string score;
							string nameId;

							packet >> data;

							// Variable for the number of clients that have sent any message, used for many purposes
							numAct++;

							// If first iteration, receive name from client and send his ID
							if (numberIterations == 0)
							{
								strcpy(name, data);

								cout << "Player " << name << endl;

								// Add data to the player's map
								// Name
								players[numAct-1].push_back(name);
								// Initial score (always cero)
								players[numAct-1].push_back("0");

								// If every client has sent message, continue with sending corresponding response
								if (numAct == MAX_PLAYERS)
								{
									// Map iterator
									map<int, std::vector<string> >::iterator i;

									i = players.begin();

									for (vector<TcpSocket*>::iterator it = clients.begin(); it != clients.end(); ++it)
									{
										TcpSocket& socket = **it;

										// Send READY, with the corresponding ID
										response = "READY:" + to_string(i->first);

										cout << response << endl;

										socket.send(response.c_str(), response.size() + 1);

										i++;
									}

									// Create a new player for the Ignorance
									// Name
									players[numAct].push_back("Ignorance");
									// Initial score (always cero)
									players[numAct].push_back("0");

									numberIterations++;
									numAct = 0;
								}

							}

							// If player sends "ACK", we need to send the corresponding question
							else if (strncmp (data,"ACK",4) == 0)
							{
								// If every client has sent message, continue with sending corresponding response
								if (numAct == MAX_PLAYERS)
								{
									for (vector<TcpSocket*>::iterator it = clients.begin(); it != clients.end(); ++it)
									{
										TcpSocket& socket = **it;

										outstring = questionsSelected[index].getQuestionRand();

										socket.send(outstring.c_str(), outstring.size() + 1);
									}

									numberIterations++;

									numAct = 0;
								}

							}

							// We reach to the last question, so we get answer adn send final score and winner
							else if (numberIterations == NUM_QUESTIONS_SELECTED*2)
							{

								size_t pos = 0;
								std::string delimiter = ":";
								string id;
								string winner;
								int ignoranceScore;

								currentBuffer = data;

								pos = currentBuffer.find(delimiter);
								answer = currentBuffer.substr(0, pos);
								currentBuffer.erase(0, pos + delimiter.length());

								pos = currentBuffer.find(delimiter);
								id = currentBuffer.substr(0, pos);

								getScore(answer, id, numAct, outstring, questionsSelected[index]);

								cout << "Answer from: " << id << " " << answer << endl;

								// If every client has sent message, continue with sending corresponding response
								if (numAct == MAX_PLAYERS)
								{
									if (numCorrect == 0)
									{
										ignoranceScore = stoi(players[MAX_PLAYERS][1]) + MIN_SCORE * MAX_PLAYERS;
										players[MAX_PLAYERS][1] = to_string(ignoranceScore);
									}

									// Map iterator
									map<int, std::vector<string> >::iterator i;

									i = players.begin();

									// Get winner
									winner = getWinner();

									for (vector<TcpSocket*>::iterator it = clients.begin(); it != clients.end(); ++it)
									{
										TcpSocket& socket = **it;

										// Add to the winner's string the final score of the player
										score = winner + ":" + i->second[1];

										socket.send(score.c_str(), score.size() + 1);

										i++;
									}

									numberIterations++;
									index++;

									numAct = 0;
								}
							}

							// We need to receive the answer and send the current score
							// Ni la primera ni la ultima pregunta
							else
							{
								size_t pos = 0;
								std::string delimiter = ":";
								string id;
								int ignoranceScore;

								currentBuffer = data;

								// Receive the answer
								pos = currentBuffer.find(delimiter);
								answer = currentBuffer.substr(0, pos);
								currentBuffer.erase(0, pos + delimiter.length());

								// Receive the player's ID
								pos = currentBuffer.find(delimiter);
								id = currentBuffer.substr(0, pos);

								// Get score
								getScore(answer, id, numAct, outstring, questionsSelected[index]);

								cout << "Answer from: " << id << " " << answer << endl;

								// If every client has sent message, continue with sending corresponding response
								if (numAct == MAX_PLAYERS)
								{
									// Map iterator
									map<int, std::vector<string> >::iterator i;

									i = players.begin();

									for (vector<TcpSocket*>::iterator it = clients.begin(); it != clients.end(); ++it)
									{
										TcpSocket& socket = **it;

										score = "SCORE:" + i->second[1];

										socket.send(score.c_str(), score.size() + 1);

										i++;
									}

									// If no player had the answer correct, the ignorance gets the maximum score possible
									if (numCorrect == 0)
									{
										ignoranceScore = stoi(players[MAX_PLAYERS][1]) + MIN_SCORE * MAX_PLAYERS;
										players[MAX_PLAYERS][1] = to_string(ignoranceScore);
									}

									numberIterations++;
									index++;

									numAct = 0;
									numCorrect = 0;
								}
							}
						}
					}
				}
			}
		}
	}
}

int main ()
{

	Question questions[NUM_QUESTIONS];
	int questionCounter = 0;

	// Read file and store every question
	string STRING;
	ifstream infile;
	infile.open ("Questions.txt");

	cout << "Reading questions from file" << endl;

	if (infile.is_open())
	{
			while(!infile.eof()) // To get you all the lines.
			{
					getline(infile,STRING); // Saves the line in STRING.
					if (STRING == "")
					{
							break;
					}
					questions[questionCounter] = addQuestion(STRING);
					questionCounter++;
			}
	}

	infile.close();

	// Select random questions for the game
	Question questionsSelected[NUM_QUESTIONS_SELECTED];
	int randCounter = 0;
	int index = 0;

	srand (unsigned (time(0)));

	cout << "Making random pool of questions for the game" << endl;

	// Dividing all the questions in groups of 5, and selecting one question from each group
	for (int i = 0; i < NUM_QUESTIONS_SELECTED; i++)
	{
		index = rand()% RAND_QUESTIONS + randCounter;
		randCounter += RAND_QUESTIONS;
		questionsSelected[i] = questions[index];
	}

	cout << "Game Ready" << endl;

	IpAddress ip = IpAddress::getLocalAddress();
	cout << "Connect to this IP: " << ip << endl;

	sendQuestions(questionsSelected);

	return 0;
}
