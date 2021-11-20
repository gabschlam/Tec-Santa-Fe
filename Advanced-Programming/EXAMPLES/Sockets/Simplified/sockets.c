/*
	Example of Sockets
	Advanced Programming
 
	Gabriel Schlam 
*/

#include "sockets.h"

// Initialize the server to be ready for connections
int startServer(char * port)
{
	struct addrinfo hints;
	struct addrinfo * server_info = NULL;
	int server_fd;
	
	// Prepare the information to determine the local address
	// Clear the structure
	bzero(&hints, sizeof hints);
	// Let the IP address be found automatically
	hints.ai_flags = AI_PASSIVE;
	// Use IPv4 address
	hints.ai_family = AF_INET;
	// Type of socket
	hints.ai_socktype = SOCK_STREAM;

	///// GETADDRINFO
	// Get the actual address of this computer
	getaddrinfo(NULL, port, &hints, &server_info);

	///// SOCKET
	// Use the information obtained by getaddrinfo
	server_fd = socket(server_info->ai_family, server_info->ai_socktype,
						server_info->ai_protocol);
	
	// Allow using the same port immediately, even if the previous server did not close correctly
	int reuse = 1;
	setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR, &reuse, sizeof reuse);

	///// BIND
	// Connect with the desired port
	bind(server_fd, server_info->ai_addr, server_info->ai_addrlen);

	// Release the memory of the list of addrinfo
	freeaddrinfo(server_info);
	
	///// LISTEN
	// Prepare for connections
	listen(server_fd, MAX_QUEUE);

	printf("Server ready and waiting!\n");

	return server_fd;
}

// Establish a connection with the server indicated by the parameters
int connectToServer(char * address, char * port)
{
	struct addrinfo hints;
	struct addrinfo * server_info = NULL;
	int client_fd;

	// Prepare the information to determine the local address
	// Clear the structure
	bzero(&hints, sizeof hints);
	// Let the IP address be found automatically
	hints.ai_flags = AI_PASSIVE;
	// Use IPv4 address
	hints.ai_family = AF_INET;
	// Type of socket
	hints.ai_socktype = SOCK_STREAM;

	///// GETADDRINFO
	// Get the actual address of this computer
	getaddrinfo(address, port, &hints, &server_info);

	///// SOCKET
	// Use the information obtained by getaddrinfo
	client_fd = socket(server_info->ai_family, server_info->ai_socktype,
						server_info->ai_protocol);

	///// CONNECT
	// Connect to the server
	connect(client_fd, server_info->ai_addr, server_info->ai_addrlen);

	freeaddrinfo(server_info);

	return client_fd;
}

int receiveMessage(int connection_fd, char * buffer, int size)
{
	int chars_read;

	// Clear the buffer
	bzero(buffer, BUFFER_SIZE);
	// Get an incomming message
	chars_read = recv(connection_fd, buffer, BUFFER_SIZE, 0);
	// Error when receiving data
	if (chars_read == -1)
	{
		perror("recv");
		close(connection_fd);
		return -1;
	}
	else if (chars_read == 0)
	{
		printf("Connection closed by client\n");
		close(connection_fd);
		return 0;
	}

	return chars_read;
}


