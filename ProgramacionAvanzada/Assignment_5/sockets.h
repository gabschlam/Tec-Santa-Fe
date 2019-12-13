/*
	Assignment 5: Blackjack - SOCKETS
	Advanced Programming
 
	Gabriel Schlam 
*/


#ifndef SOCKETS_H
#define SOCKETS_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>

// Include libraries for sockets
#include <netdb.h>
#include <arpa/inet.h>

#define SERVICE_PORT 8642
#define MAX_QUEUE 5
#define BUFFER_SIZE 1024

int startServer(char * port);
int connectToServer(char * address, char * port);
int receiveMessage(int connection_fd, char * buffer, int size);

#endif

