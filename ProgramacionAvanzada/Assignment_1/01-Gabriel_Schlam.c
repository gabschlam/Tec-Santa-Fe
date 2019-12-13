/*
	Assignment 1
	Advanced Programming
 
	Gabriel Schlam 
*/

#include <stdio.h>
#include <stdlib.h> // INCLUDE FOR rand(), srand() FUNCTIONS
#include <time.h> // INCLUDE FOR time() FUNCTION
#include <unistd.h>

#define ARR_SIZE 30

int main() {
	
	FILE *file; // INITIALIZATION OF FILE
	int arr[ARR_SIZE]; // INITIALIZATION OF ARRAY
	int num; // NUMBER ASKED TO USER
	char file_name[] = "differences.txt"; // DIFFERENCES FILE NAME
	
	/* THIS IS BECAUSE, WHEN TESTING THE PROGRAM, THE SAME VALUES CAME OUT IN THE ARRAY
	https://stackoverflow.com/questions/1108780/why-do-i-always-get-the-same-sequence-of-random-numbers-with-rand
	*/
	srand(time(0));

	printf("Creating array...\n");

	for (int i = 0; i < ARR_SIZE; i++) {
		arr[i] = rand() % 100 + 1; // FILLING ARRAY WITH RANDOM NUMBERS BETWEEN 1 AND 100
	}

	printf("Array created\n" );

	printf("Enter a number in a range from 1 to 100: ");
	scanf("%d", &num); // ASSIGN ASKED VALUE TO VARIABLE num
	
	file = fopen(file_name, "w"); // OPEN FILE, FOR WRITING ("w")
	
	for (int i = 0; i < ARR_SIZE; i++) {
		int dif = num - arr[i]; // CALCULATE DIFFERENCE BETWEEN GIVEN NUMBER AND NUMBERS FROM ARRAY
		fprintf(file, "%d\n", dif); // WRITE DIFFERENCE TO FILE
	}

	printf("Check file \"%s\"\n", file_name);

	fclose(file); // CLOSE FILE

	return 0;
	
}
