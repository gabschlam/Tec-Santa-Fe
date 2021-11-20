/*
	First test of C
	Advanced Programming
 
	Gabriel Schlam 
*/

#include <stdio.h>
#include <string.h>

#define STR_SIZE 50

int main()
{
	char name[STR_SIZE];
	int age;
	char gender;

	printf("What is your name? ");
	//scanf("%s", name); HACE ERROR AL LEER MAS CARACTERES DE LOS QUE PUEDE GUARDAR
	fgets(name, STR_SIZE, stdin); // MEJOR POR EL PROBLEMA DE SCANF DE ARRIBA

	// REMOVE THE NEWLINE CHARACTER
	name[strlen(name)-1] = '\0';
	printf("Hello %s\n", name);

	printf("Enter your age: ");
	scanf("%d", &age);
	printf("You are %d years old \n", age);

	printf("Enter your gender: ");
	scanf(" %c", &gender); // PUT A SPACE BEFORE THE %c TO AVOID SAVING THE ENTER BEFORE
	printf("Your gender is: %c\n", gender);

	return 0;
}