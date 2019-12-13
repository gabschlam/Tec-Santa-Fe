/*
	Assignment 4: 
	Vigenere Cipher
	Advanced Programming
 
	Gabriel Schlam 
*/

#include "vigenere_cipher.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h> // UNIX functions
#include <ctype.h>  // FOR FUNCTIONS: tolower(), isalpha()

#define STR_SIZE 50

// Function definitions
void preparePipes(int * in_pipe, int * out_pipe)
{
	// Close the pipe ends that will not be used
	close(in_pipe[1]); // Close the writing file descriptor
	close(out_pipe[0]); // Close the reading file descriptor
}

void encodeCipher(char file_name[STR_SIZE], char * key, char file_res[STR_SIZE])
{
	FILE *file_plainText;
	FILE *file_cipherText;
	char c;
	int cipherVal;
	int i = 0; // Counter for the key
	int len = strlen(key); // To determine if the key word needs to reset
	
	file_plainText = fopen(file_name, "r");

	if (file_plainText == NULL) {
		printf("Opening file failed\n");
		printf("Exiting program...\n");
		exit(EXIT_FAILURE);
	}

	file_cipherText = fopen(file_res, "w");
	
	if (file_cipherText == NULL) {
		printf("Opening file failed\n");
		printf("Exiting program...\n");
		exit(EXIT_FAILURE);
	}

	else {
		while ((c = fgetc(file_plainText)) != EOF) // While there is text in the file
		{
			if(isalpha(c)) { // If the character is a letter
				/* 	As the character is lowercase, the letters range is (97 to 122)
					Encryption formula: Ei = ((Pi - 97 + Ki - 97) % 26) + 97
					Substract to the character and to the key the first value of range (97)
					% 26 is because there are 26 letters on the alphabet 
					+ 97 is to return the value to the letters range  */
				cipherVal = ((int)c - 97 + (int)tolower(key[i % len]) - 97 ) % 26 + 97;
			}

			else { // If the character is not a letter, leave it the same (ex. for spaces, new line)
				cipherVal = c;
			}

			fprintf(file_cipherText, "%c", (char)cipherVal); // Write in the file

			i++; // Add to the counter for the key
		}
	}
	
	fclose(file_plainText);
	fclose(file_cipherText);
}

void decodeCipher(char file_name[STR_SIZE], char * key, char file_res[STR_SIZE])
{
	FILE *file_plainText;
	FILE *file_cipherText;
	char c;
	int cipherVal;
	int i = 0; // Counter for the key
	int len = strlen(key); // To determine if the key word needs to reset
	
	file_cipherText = fopen(file_name, "r");

	if (file_cipherText == NULL) {
		printf("Opening file failed\n");
		printf("Exiting program...\n");
		exit(EXIT_FAILURE);
	}

	file_plainText = fopen(file_res, "w");
	 
	if (file_plainText == NULL) {
		printf("Opening file failed\n");
		printf("Exiting program...\n");
		exit(EXIT_FAILURE);
	}

	else {
		while ((c = fgetc(file_cipherText)) != EOF) // While there is text in the file
		{
			if(isalpha(c)) { // If the character is a letter
				/* 	As the character is lowercase, the letters range is (97 to 122)
					Decryption formula: Pi = ((Ei – Ki + 26) % 26) + 97
					% 26 is because there are 26 letters on the alphabet 
					+ 97 is to return the value to the letters range  */
				cipherVal = ((int)c - (int)tolower(key[i%len]) + 26);

				if (cipherVal < 0) // If the cipher value is negative, change it to positive
				{
					cipherVal = cipherVal * -1;
				}
				cipherVal = (cipherVal % 26) + 97;			
			}

			else { // If the character is not a letter, leave it the same (ex. for spaces, new line)
				cipherVal = c;
			}

			fprintf(file_plainText, "%c", (char)cipherVal); // Write in the file

			i++; // Add to the counter for the key
		}
	}
	
	fclose(file_plainText);
	fclose(file_cipherText);
}

