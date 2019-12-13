/*
	Assignment 5: Blackjack - SERVER
	Advanced Programming
 
	Gabriel Schlam 
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include <time.h>

// Include libraries for sockets
#include <netdb.h>
#include <arpa/inet.h>
// Include own socket library
#include "sockets.h"

#define SERVICE_PORT 8642
#define MAX_QUEUE 5
#define BUFFER_SIZE 1024

void usage(char * program);
void waitForConnections(int server_fd);
void communicationLoop(int connection_fd);
int playBlackjack(int money, int bet, int connection_fd);
int drawCard(int connection_fd);
void printCard(int val, int connection_fd);
int dealPlayer(int connection_fd);
int dealDealer(int connection_fd);
int determineWinner(int handPlayer, int handDealer, int money, int bet, int connection_fd);

int main(int argc, char * argv[])
{
	int server_fd;

	printf("\n=== SERVER PROGRAM ===\n");

	if (argc != 2)
		usage(argv[0]);

	server_fd = startServer(argv[1]);
	waitForConnections(server_fd);

	printf("Closing the server socket\n");
	// Close the socket
	close(server_fd);

	return 0;
}

// Show the user how to run this program
void usage(char * program)
{
	printf("Usage:\n%s {port_number}\n", program);
	exit(EXIT_FAILURE);
}

// Stand by for connections by the clients
void waitForConnections(int server_fd)
{
	struct sockaddr_in client_address;
	socklen_t client_address_size;
	char client_presentation[INET_ADDRSTRLEN];
	int connection_fd;
	pid_t new_pid;

	while(1)
	{
		///// ACCEPT
		// Receive incomming connections
		// Get the size of the structure where the address of the client will be stored
		client_address_size = sizeof client_address;

		// Get the file descriptor to talk with the client
		connection_fd = accept(server_fd, (struct sockaddr *) &client_address, &client_address_size);

		// Fork a child process to deal with the new client
		new_pid = fork();
		if (new_pid > 0)    // Parent
		{
			close(connection_fd);
		}
		else if (new_pid == 0)      // Child
		{
			close(server_fd);

			// Identify the client
			// Get the ip address from the structure filled by accept
			inet_ntop(client_address.sin_family, &client_address.sin_addr, client_presentation, INET_ADDRSTRLEN);
			printf("Received a connection from %s : %d\n", client_presentation, client_address.sin_port);

			// Establish the communication
			communicationLoop(connection_fd);
			close(connection_fd);
			// Finish the child process
			exit(EXIT_SUCCESS);
		}
	}
}

// Do the actual receiving and sending of data
void communicationLoop(int connection_fd)
{
	char buffer[BUFFER_SIZE];
	char * string;
	int chars_read;
	int money;
	int bet;

	// Handshake: START:MONEY
	chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);

	// Get the first part: Start
	string = strtok(buffer, ":"); // Separate string with : in between
	if (strncmp(string, "START", 6) != 0)
	{
		printf("Invalid client. Exiting\n");
		return;
	}

	// Get the second part: Amount of money
	string = strtok(NULL, ":");
	money = atoi(string);

	// Send a reply; Game Ready
	sprintf(buffer, "READY");
	send(connection_fd, buffer, strlen(buffer)+1, 0);

	while(1)
	{
		// Recieves: PLAY:BET 
		chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);
		if (chars_read <= 0)
			break;

		// Get the first part: Play
		string = strtok(buffer, ":");

		// Get the second part: Bet
		string = strtok(NULL, ":");
		bet = atoi(string);

		// Play, and receive remaining money
		money = playBlackjack(money, bet, connection_fd);

		// Determine if client wants to play another hand
		// Recieves: HAND / EXIT 
		chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);
		if (chars_read <= 0)
			break;

		if (strncmp(buffer, "HAND", 5) == 0)
		{
			continue;
		}
		else if (strncmp(buffer, "EXIT", 5) == 0)
		{
			// Final message to close the connection
			send(connection_fd, "BYE", 4, 0);
			printf("Connection closed by client\n");
			break;
		}
	}
}

int playBlackjack(int money, int bet, int connection_fd)
{
	char buffer[BUFFER_SIZE];
	int handPlayer;
	int handDealer;

	// Initialize the random seed
	srand(time(NULL));

	// Reset variables
	handPlayer = 0;
	handDealer = 0;

	handPlayer = dealPlayer(connection_fd); // Player's turn
	handDealer = dealDealer(connection_fd); // Dealer's turn

	// Send Dealer's hand
	sprintf(buffer, "Dealer's hand: %d", handDealer);
	send(connection_fd, buffer, strlen(buffer)+1, 0);

	// Get winner and remaining money
	money = determineWinner(handPlayer, handDealer, money, bet, connection_fd);
	printf("Remaining money: $%d\n", money);

	return money;
}

int drawCard(int connection_fd)
{
	int card;
	card = rand()%13 + 1;

	printCard(card, connection_fd); // Print card and send it to client

	if (card == 11 || card == 12 || card == 13)
	{
		card = 10;
	}
	return card;
}

void printCard(int val, int connection_fd)
{
	char buffer[BUFFER_SIZE];

	if (val == 1) 
	{
		sprintf(buffer, "Card: A");
		send(connection_fd, buffer, strlen(buffer)+1, 0);
		//printf("Card: A \n");
	}
	else if (val > 1 && val <= 10) 
	{
		sprintf(buffer, "Card: %d", val);
		send(connection_fd, buffer, strlen(buffer)+1, 0);
		//printf("Card: %d \n", val);
	}
	else if (val == 11) 
	{
		sprintf(buffer, "Card: J");
		send(connection_fd, buffer, strlen(buffer)+1, 0);
		//printf("Card: J \n");
	}
	else if(val == 12) 
	{
		sprintf(buffer, "Card: Q");
		send(connection_fd, buffer, strlen(buffer)+1, 0);
		//printf("Card: Q \n");
	}
	else if (val == 13)
	{
		sprintf(buffer, "Card: K");
		send(connection_fd, buffer, strlen(buffer)+1, 0);
		//printf("Card: K \n");
	}
}

int dealPlayer(int connection_fd)
{
	char buffer[BUFFER_SIZE];
	int chars_read = 0;
	int aceCounter = 0;
	int hand = 0;
	int newCard = 0;
	
	printf("Player's Turn \n");

	// Player draws first 2 cards
	for (int i = 0; i < 2; ++i)
	{
		newCard = drawCard(connection_fd);

		// Get acknowledgment of card received to continue sending
		chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);
		if (chars_read <= 0)
			break;

		if (newCard == 1) // If newCard is an Ace
		{
			aceCounter++;
			hand += 11;
		}
		else
		{
			hand += newCard;
		}
	}
	
	// Substract hand -10 if there is an ace and the hand is over 21
	if (hand > 21 && aceCounter > 0)
	{
		hand -= 10;
		aceCounter = 0;
	}

	// Send total hand to client
	sprintf(buffer, "Hand: %d", hand);
	send(connection_fd, buffer, strlen(buffer)+1, 0);
	//printf("Hand = %d\n", hand);

	// If the player has 21 or less, he can choose to draw more cards or stay
	while (hand <= 21)
	{
		// Recieve option if wants hit or stay
		chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);
		if (chars_read <= 0)
			break;

		// Receive message
		if (strncmp(buffer, "STAY", 5) == 0)
		{
			return hand;
		}
		else if (strncmp(buffer, "HIT", 4) == 0)
		{
			newCard = drawCard(connection_fd); // Draw new card

			// Get acknowledgment of card received to continue sending
			chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);
			if (chars_read <= 0)
				break;

			if (newCard == 1) // If newCard is an Ace
			{
				aceCounter++;
				hand += 11;
			}
			else
			{
				hand += newCard;
			}

			// Substract hand -10 if there is an ace and the hand is over 21
			if (hand > 21 && aceCounter > 0)
			{
				hand -= 10;
				aceCounter = 0;
			}
			
			// Send total hand to client
			sprintf(buffer, "Hand: %d", hand);
			send(connection_fd, buffer, strlen(buffer)+1, 0);
			//printf("Hand = %d\n", hand);
		}
	}

	// If hand was over 21, also receive stay for continue
	chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);
	if (chars_read <= 0)
		return -1;

	// Receive message
	if (strncmp(buffer, "STAY", 5) == 0)
	{
		return hand;
	}

	return -1;
}

int dealDealer(int connection_fd)
{
	char buffer[BUFFER_SIZE];
	int chars_read = 0;
	int aceCounter = 0;
	int hand = 0;
	int newCard = 0;
	
	printf("Dealer's cards sent \n");

	// If the dealer has 17 or less it has to draw a card
	while (hand <= 17)
	{
		newCard = drawCard(connection_fd);

		// Get acknowledgment of card received to continue sending
		chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);
		if (chars_read <= 0)
			break;

		if (newCard == 1) // If newCard is an Ace
		{
			aceCounter++;
			hand += 11;
		}
		else
		{
			hand += newCard;
		}

		// Substract hand -10 if there is an ace and the hand is over 21
		if (hand > 21 && aceCounter > 0)
		{
			hand -= 10;
			aceCounter = 0;
		}

		//printf("Hand = %d\n", hand);
	}
	return hand;
}

int determineWinner(int handPlayer, int handDealer, int money, int bet, int connection_fd)
{
	char buffer[BUFFER_SIZE];
	int chars_read;

	// Recieve acknowledgment to continue
	chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);
	if (chars_read <= 0)
		return -1;

	if ((handDealer > 21 && handPlayer > 21) || handDealer == handPlayer)
	{
		sprintf(buffer, "TIE:%d", money);
		send(connection_fd, buffer, strlen(buffer)+1, 0);
		printf("Result: It's a tie!\n");
	}
	else if ((handDealer > handPlayer && handDealer <= 21) || handPlayer > 21)
	{
		money -= bet;
		sprintf(buffer, "DEALER:%d", money);
		send(connection_fd, buffer, strlen(buffer)+1, 0);
		printf("Result: The dealer wins!\n");
	}
	else
	{
		money += bet;
		sprintf(buffer, "PLAYER:%d", money);
		send(connection_fd, buffer, strlen(buffer)+1, 0);
		printf("Result: The player wins!\n");
	}
	return money;
}

