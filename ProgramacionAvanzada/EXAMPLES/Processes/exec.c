/*
	Exec functions
	Advanced Programming
 
	Gabriel Schlam 
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h> // UNIX functions

int main()
{
	//char * command = "ls -l /";
	char * command = "/bin/ls";

	printf("BEFORE EXEC\n");

	execl(command, "ls", "-l", "/", (char *) NULL);

	// system(command); 	RUN COMMAND AND RETURN BACK TO THIS PROGRAM

	printf("AFTER EXEC\n");

	return 0;
}