/*
	Example of Sockets: SERVER
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

int main(int argc, char * argv[])
{
	int server_fd;

	printf("\n=== SERVER PROGRAM ===\n");

	if (argc != 2)
		usage(argv[0]);

	// Show the ip's for this machine
	printLocalIPs();

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
	int guess_counter = 0;
	int chars_read;
	int limit;
	int target;
	int guess;

	// Initialize the random seed
	srand(time(NULL));

	// Handshake
	chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);
	// Get the first part
	string = strtok(buffer, ":");
	if (strncmp(string, "START", 6) != 0)
	{
		printf("Invalid client. Exiting!\n");
		return;
	}
	// Get the second part, with the limit number
	string = strtok(NULL, ":");
	limit = atoi(string);

	// Generate random number
	target = rand() % limit + 1;
	printf("The number to guess is << %d >>\n", target);

	// Send a reply
	sprintf(buffer, "READY");
	send(connection_fd, buffer, strlen(buffer)+1, 0);

	while(1)
	{
		chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);
		if (chars_read <= 0)
			break;
		string = strtok(buffer, ":");
		if (strncmp(string, "GUESS", 6) != 0)
		{
			printf("Invalid client. Exiting!\n");
			return;
		}
		// Get the second part, with the limit number
		string = strtok(NULL, ":");
		guess = atoi(string);
		guess_counter++;
		printf("The client sent me: '%d'\n", guess);

		if (guess == target)
		{
			sprintf(buffer, "OK");
			send(connection_fd, buffer, strlen(buffer)+1, 0);
			break;
		}
		else if (guess > target)
		{
			sprintf(buffer, "LOWER");
		}
		else
		{
			sprintf(buffer, "HIGHER");
		}
		// Send a reply
		send(connection_fd, buffer, strlen(buffer)+1, 0);
	}

	chars_read = receiveMessage(connection_fd, buffer, BUFFER_SIZE);
	printf("The client sent me: '%s'\n", buffer);
	if (strncmp(buffer, "BYE", 4) != 0)
	{
		printf("Invalid client. Exiting!\n");
		return;
	}
	// Final message to close the connection
	send(connection_fd, "BYE", 4, 0);
}


