/*
	Assignment 5: Blackjack - CLIENT
	Advanced Programming
 
	Gabriel Schlam 
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include <netdb.h>
#include "sockets.h"
// Include own sockets library
#include "sockets.h"

#define SERVICE_PORT 8642
#define BUFFER_SIZE 1024

void usage(char * program);
void communicationLoop(int connection_fd);

int main(int argc, char * argv[])
{
	int client_fd;

	printf("\n=== CLIENT PROGRAM ===\n");

	if (argc != 3)
		usage(argv[0]);

	client_fd = connectToServer(argv[1], argv[2]);
	communicationLoop(client_fd);

	// Closing the socket
	printf("Closing the connection socket\n");
	close(client_fd);

	return 0;
}

// Show the user how to run this program
void usage(char * program)
{
	printf("Usage:\n%s {server_address} {port_number}\n", program);
	exit(EXIT_FAILURE);
}

// Do the actual receiving and sending of data
void communicationLoop(int connection_fd)
{
	char buffer[BUFFER_SIZE];
	char choice;
	char * string;
	int chars_read;
	int money;
	int bet;

	printf("Welcome to the Blackjack game!\n");
	printf("Enter the maximum amount of money you want to bet: ");
	scanf("%d", &money);

	// Handshake
	// Send a request to start the game
	sprintf(buffer, "START:%d", money);
	send(connection_fd, buffer, strlen(buffer)+1, 0);

	// Recieves a confirmation that the game is ready
	chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);
	if (strncmp(buffer, "READY", 6) != 0)
	{
		printf("Invalid server. Exiting\n");
		return;
	}

	printf("Game ready!\n");

	while(1)
	{
		int hand;

		printf("Enter your bet: ");
		scanf("%d", &bet);

		while (bet > money)
		{
			printf("Current money is less than the bet \n");
			printf("Enter another bet: ");
			scanf("%d", &bet);
		}
		
		// Send the bet made
		sprintf(buffer, "PLAY:%d", bet);
		send(connection_fd, buffer, strlen(buffer)+1, 0);

		printf("\n=== YOUR TURN ===\n");

		// Get first two cards and hand
		// Recieve first card
		chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);
		printf("%s\n", buffer);
		// Send acknowledgment
		sprintf(buffer, "ACK");
		send(connection_fd, buffer, strlen(buffer)+1, 0);
		// Recieve second card
		chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);
		printf("%s\n", buffer);
		// Send acknowledgment
		sprintf(buffer, "ACK");
		send(connection_fd, buffer, strlen(buffer)+1, 0);
		// Recieve hand
		chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);
		printf("%s\n", buffer);

		// Check if hand is 21 or more
		// Get the first part
		string = strtok(buffer, ":"); // Separate string with : in between
		// Get the second part, with the hand
		string = strtok(NULL, ":");
		hand = atoi(string);
		if (hand < 21)
		{
			printf("Do you want another card? (y/n) ");
			scanf(" %c",&choice);
			getchar(); // To consume the newline
		}
		else if (hand == 21)
		{
			printf("Blackjack!\n");
		}
		else
		{
			printf("Busted at %d\n", hand);
		}

		if (choice == 'y')
		{
			while (choice == 'y')
			{
				sprintf(buffer, "HIT");
				send(connection_fd, buffer, strlen(buffer)+1, 0);

				// Recieve next card and hand
				// Recieve card
				chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);
				printf("%s\n", buffer);
				// Send acknowledgment
				sprintf(buffer, "ACK");
				send(connection_fd, buffer, strlen(buffer)+1, 0);
				// Recieve hand
				chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);
				printf("%s\n", buffer);

				// Check if hand is 21 or more
				// Get the first part
				string = strtok(buffer, ":"); // Separate string with : in between
				// Get the second part, with the hand
				string = strtok(NULL, ":");
				hand = atoi(string);
				if (hand <= 21)
				{
					printf("Do you want another card? (y/n) ");
					scanf(" %c",&choice);
					getchar(); // To consume the newline
				}
				else
				{
					printf("Busted at %d\n", hand);
					break;
				}
			}

			printf("\n=== DEALER'S TURN ===\n");
			sprintf(buffer, "STAY");
			send(connection_fd, buffer, strlen(buffer)+1, 0);	
		}
		else
		{
			printf("\n=== DEALER'S TURN ===\n");	
			sprintf(buffer, "STAY");
			send(connection_fd, buffer, strlen(buffer)+1, 0);
		}



		while(1)
		{
			// Recieve card dealer
			chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);

			// Get the first part
			string = strtok(buffer, ":"); // Separate string with : in between

			if (strncmp(buffer, "Card", 5) == 0) // Recieve dealer's card
			{
				printf("%s:%s\n", string, strtok(NULL, ":"));

				// Send acknowledgment
				sprintf(buffer, "ACK");
				send(connection_fd, buffer, strlen(buffer)+1, 0);
			}
			else if (strncmp(buffer, "TIE", 4) == 0)
			{
				printf("\n=== RESULT: ===\n");
				printf("It's a tie!\n");
				break;
			}
			else if (strncmp(buffer, "DEALER", 7) == 0)
			{
				printf("\n=== RESULT: ===\n");
				printf("The dealer wins!\n");
				break;
			}
			else if (strncmp(buffer, "PLAYER", 7) == 0)
			{
				printf("\n=== RESULT: ===\n");
				printf("You win!\n");
				break;
			}
			else // Recieve dealer's hand
			{
				printf("%s:%s\n", string, strtok(NULL, ":"));

				// Send acknowledgment
				sprintf(buffer, "ACK");
				send(connection_fd, buffer, strlen(buffer)+1, 0);
				continue;
			}
		}

		// Get the second part, with the remaining money
		string = strtok(NULL, ":");
		money = atoi(string);

		if (money <= 0)
		{
			printf("You have no money left.\n");
			break;
		}

		else
		{
			printf("You have $%d left.\n", money);

			printf("Do you want to play another hand? (y/n): ");
			scanf(" %c",&choice);
			getchar(); // To consume the newline
		}
		
		if (choice != 'y')
		{
			break;
		}

		// Send HAND for new hand
		sprintf(buffer, "HAND");
		send(connection_fd, buffer, strlen(buffer)+1, 0);	
	}

	// Final message to close the connection
	send(connection_fd, "EXIT", 5, 0);
	chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);
	printf("\nThank you for playing Blackjack!\n");
}

