/*
	Assignment 4: 
	Vigenere Cipher
	Advanced Programming
 
	Gabriel Schlam 
*/

#ifndef VIGENERE_CIPHER_H
#define VIGENERE_CIPHER_H

//#include <stdio.h>
//#include <stdlib.h>
//#include <string.h>
//#include <unistd.h> // UNIX functions
//#include <ctype.h>  // FOR FUNCTIONS: tolower(), isalpha()

#define STR_SIZE 50

// Function declarations
void preparePipes(int * in_pipe, int * out_pipe);
void encodeCipher(char file_name[STR_SIZE], char * key, char file_res[STR_SIZE]);
void decodeCipher(char file_name[STR_SIZE], char * key, char file_res[STR_SIZE]);

#endif

