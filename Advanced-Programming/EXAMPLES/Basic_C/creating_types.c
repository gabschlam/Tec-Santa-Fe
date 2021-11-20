/*
	Example of using struct / union / enum / typedef
	Advanced Programming
 
	Gabriel Schlam 
*/

#include <stdio.h>
#include <string.h>

#define NAME_LENGTH 60

// typedef: FOR RENAME A DATATYPE
typedef struct person {
	char name[NAME_LENGTH];
	int age;
	float height;
	float weight;
} person_t; // NEW NAME FOR DATA

// DECLARATION OF FUNCTIONS
void printPerson(person_t anyone);

int main()
{
	person_t Gabriel;

	strncpy(Gabriel.name, "Gabriel Schlam", NAME_LENGTH);
	Gabriel.age = 21;
	Gabriel.height = 1.80;
	Gabriel.weight = 70;

	printPerson(Gabriel);

	return 0;
}

// FUNCTION DEFINITIONS
void printPerson(person_t anyone) {
	printf("Size of the structure: %ld\n", sizeof(anyone)); // SIZE FOR THE STRUCT IN BYTES

	printf("Name: %s\n", anyone.name);
	printf("Age: %d\n", anyone.age);
	printf("Height %.2f\n", anyone.height);
	printf("Weight %.1f\n", anyone.weight);
}