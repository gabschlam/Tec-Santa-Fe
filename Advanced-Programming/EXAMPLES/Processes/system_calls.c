/*
	Test of system calls
	Advanced Programming
 
	Gabriel Schlam 
*/

#include <stdio.h>
#include <stdlib.h>

int main()
{
	int status;
	char * command = "ls /";

	status = system(command);
	printf("System returned %d\n", status);

	/*
	command = "pstree";
	status = system(command);
	printf("System returned %d\n", status);
	*/

	return 0;
}