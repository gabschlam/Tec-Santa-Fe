/*
	Assignment 4: 
	Test Program for Vigenere Cipher
	Advanced Programming
 
	Gabriel Schlam 
*/

#include "vigenere_cipher.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h> // UNIX functions

#define BUFFER_LENGTH 100
#define STR_SIZE 50

// Function declarations
void preparePipes(int * in_pipe, int * out_pipe);
void encodeCipher(char file_name[STR_SIZE], char * key, char file_res[STR_SIZE]);
void decodeCipher(char file_name[STR_SIZE], char * key, char file_res[STR_SIZE]);

int main()
{
	pid_t new_pid;
	int opt;
	int status;
	char file_name[STR_SIZE];
	char file_res[STR_SIZE];
	char key[STR_SIZE];
	char buffer[BUFFER_LENGTH];

	// Pipe arrays
	int parent_to_child[2];
	int child_to_parent[2];

	printf("Do you want to encode or decode a message? (Select option 1 or 2)\n");
	printf("1 = encode\n");
	printf("2 = decode\n");
	scanf("%d", &opt);
	getchar(); // For eliminating the new line character

	if (opt < 1 || opt > 2) { // If options is incorrect
		printf("Option incorrect\n");
		exit(EXIT_FAILURE);
	}

	printf("Enter file name: ");
	fgets(file_name, STR_SIZE, stdin);
	file_name[strlen(file_name)-1] = '\0'; // Remove the new line character

	printf("Enter key: ");
	fgets(key, STR_SIZE, stdin);
	key[strlen(key)-1] = '\0'; // Remove the new line character

	// Open the pipes
	status = pipe(parent_to_child);
	if (status < 0) { // Error while creating pipe
		perror("PIPE");
		exit(EXIT_FAILURE);
	}
	status = pipe(child_to_parent); // Error while creating pipe
	if (status < 0) {
		perror("PIPE");
		exit(EXIT_FAILURE);
	}

	// Create a new process
	new_pid = fork();

	// Identify the processes
	if (new_pid > 0)	// Parent process
	{
		preparePipes(child_to_parent, parent_to_child);
		read(child_to_parent[0], buffer, BUFFER_LENGTH); // Read the new file name from child

		if (strcmp(buffer, "") == 0) // If the file couldn't be open, the buffer from child would be empty
		{
			exit(EXIT_FAILURE);
		}
		
		else {
			printf("The new text is in the file: %s\n", buffer);
		}

		printf("Parent finishing\n");
	}

	else if (new_pid == 0) // Child process
	{
		preparePipes(parent_to_child, child_to_parent);

		switch(opt){
			case 1:
				strcpy(file_res, "encoded_"); // Initialize file_res
				strcat(file_res, file_name); // Concatenate file_res with the original file name
				encodeCipher(file_name, key, file_res);
				write(child_to_parent[1], file_res, strlen(file_res)+1); // Send to parent the new file name
				break;
			case 2:
				strcpy(file_res, "decoded_"); // Initialize file_res
				strcat(file_res, file_name); // Concatenate file_res with the original file name		
				decodeCipher(file_name, key, file_res);
				write(child_to_parent[1], file_res, strlen(file_res)+1); // Send to parent the new file name
				break;
		}
	}

	else {
		printf("ERROR when doing the fork\n");
	}

	return 0;
}
